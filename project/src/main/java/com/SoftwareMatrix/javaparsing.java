package com.SoftwareMatrix;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class javaparsing {
    CompilationUnit cu;
    Optional<ClassOrInterfaceDeclaration> classA;

    List<Integer> branch_pos;
    /**
     *   Constructor of basic java parser. Standard Unit is a class file.
     *
     * @param file_path : actual file path for parsing
     * @param class_name : class name in the file
     *
     **/
    javaparsing(String file_path, String class_name) throws FileNotFoundException {
        cu = StaticJavaParser.parse(new File(file_path));
        classA = cu.getClassByName(class_name);
        branch_pos = new ArrayList<Integer>();
    }

    /*
    List<String> get_operands_class()
    {
        List<String> operands = new ArrayList<String>();
    }
    List<String> get_operands_method(String method_name)
    {

    }

    List<String> get_operators_class()
    {
        List<String> operators = new ArrayList<String>();
    }
    List<String> get_operators_method(String method_name)
    {

    }
*/

    /**
     * Give location of each branch. should be done after get_branch_num()
     * @return List of positions of branches
     */
    List<Integer> get_branch_pos(){
        return branch_pos;
    }
    /**
     * count the number of branch - if / switch / while / for
     * Assume switch statement has no default context
     * Only includes implementation in current file(cu)
     *
     * @return number of branch
     */
    int get_branch_num(){
        int sum=0;
        for(MethodDeclaration mitr : classA.get().getMethods()) {
            //for debug
            //System.out.println("name: " + mitr.getName());
            BlockStmt bStmt = new BlockStmt();
            for (Node itr : mitr.getChildNodes()) {
                if (itr instanceof BlockStmt)
                    bStmt = (BlockStmt) itr;
            }
            //System.out.println(calc(bStmt));
            sum += calc(bStmt);
        }
        return sum;
    }

    /**
     * get methods list from the class
     * @return method list
     */
    List<MethodDeclaration> get_list_method(){
        List<MethodDeclaration> ret = new ArrayList<MethodDeclaration>();
        for(MethodDeclaration mitr : classA.get().getMethods())
            ret.add(mitr);
        return ret;
    }

    /**
     * get branch number in specific method
     * @param method : method that wants to be counted
     * @return the number of branch
     */
    int get_branch_num_method(MethodDeclaration method){
        return calc(method.getBody().get());
    }

    /**
     * count the number of branch of each method
     * No consideration about inner jobs, which mean, if two contexts divided by one branch has same operations,
     * Still considered as different context.
     *
     * @param root : root BlockStmt in the AST node, which contains whole implementation of method.
     * @return the number of branch, same with get_branch_num()
     */
    int calc(BlockStmt root) {
        int complexity = 0;
        for(Statement itr : root.getStatements())
        {
            if(itr.isIfStmt() || itr.isSwitchStmt() || itr.isForStmt() || itr.isWhileStmt())
                complexity += calc_recur(itr);
        }
        return complexity;
    }

    /**
     * count the number of branch, but consider only one statement each.
     * By recursive calls, it can deal with inner branches.
     * For example, if(if())
     *
     * @param node : each "branch" statement to count
     * @return the number of branch in one statement
     */
    int calc_recur(Statement node) {
        if(!node.isIfStmt() && !node.isSwitchStmt() && !node.isForStmt() && !node.isWhileStmt())
            return 0;

        branch_pos.add(node.getRange().get().begin.line);

        int complexity = 0;
        if(node.isSwitchStmt()) {
            complexity += node.asSwitchStmt().getEntries().size();
            for(int i=0 ; i<node.asSwitchStmt().getEntries().size(); i++)
            {
                for(int j=0 ; j<node.asSwitchStmt().getEntry(i).getStatements().size(); j++)
                    complexity += calc_recur(node.asSwitchStmt().getEntry(i).getStatement(j));
            }
            //Assume no default
        }
        if(node.isIfStmt()) {
            complexity++;
            complexity += calc_recur(node.asIfStmt().getThenStmt());
            if (node.asIfStmt().hasElseBranch()) {
                complexity += calc_recur(node.asIfStmt().getElseStmt().get());
            }
        }
        if(node.isForStmt())
        {
            complexity++;
            complexity += calc_recur(node.asForStmt().getBody());
        }
        if(node.isWhileStmt())
        {
            complexity++;
            complexity += calc_recur(node.asWhileStmt().getBody());
        }
        return complexity;
    }
}

