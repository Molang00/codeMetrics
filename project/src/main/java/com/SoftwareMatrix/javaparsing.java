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
import java.util.Optional;

public class javaparsing {
    CompilationUnit cu;
    Optional<ClassOrInterfaceDeclaration> classA;
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
    }

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
    int calc(BlockStmt root) {
        int complexity = 0;
        for(Statement itr : root.getStatements())
        {
            if(itr.isIfStmt() || itr.isSwitchStmt() || itr.isForStmt() || itr.isWhileStmt())
                complexity += calc_recur(itr);
        }
        return complexity;
    }
    int calc_recur(Statement node) {
        if(!node.isIfStmt() && !node.isSwitchStmt() && !node.isForStmt() && !node.isWhileStmt())
            return 0;
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

