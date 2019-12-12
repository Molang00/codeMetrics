package com.SoftwareMatrix;

import com.SoftwareMatrix.PageFactory.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiElement;
import com.SoftwareMatrix.metrics.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.util.*;

import org.jetbrains.annotations.NotNull;


public class MetricsResultWindow implements UpdateObserver {
    /* Declare private fields here */
    private JPanel myToolWindowContent;
    private RefactorPageFactory currentPageFactory; // state of this FSM
    private Map<String, RefactorPageFactory> pfMap;

    private UpdateManager uManager;
    private Stack<String> history;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow, @NotNull Project project) {
        uManager = UpdateManager.getInstance(project); // init update manager
        myToolWindowContent = new JPanel();
        pfMap = new HashMap<>();
        history = new Stack<>();

        Metric AHF = new AHFMetric("AHF", 1, 0);
        Metric AIF = new AIFMetric("AIF", 0, 1);
        Metric AMS = new AMSMetric("AMS");
        Metric CLOC = new CLOCMetric("CLOC");
        Metric Cyclomatic = new CyclomaticMetric("Cyclomatic", 20, 0);
        Metric DistinctOperand = new DistinctOperandMetric("DistinctOperand");
        Metric DistinctOperator = new DistinctOperatorMetric("DistinctOperator");
        Metric DIT = new DITMetric("DIT");
        Metric HalsteadDifficulty = new DifficultyMetric("HalsteadDifficulty");
        Metric HalsteadEffort = new EffortMetric("HalsteadEffort");
        Metric HalsteadVolume = new HalsteadVolumeMetric("HalsteadVolume");
        Metric LLOC = new LLOCMetric("LLOC");
        Metric LOC = new LOCMetric("LOC");
        Metric Maintainability = new MaintainabilityMetric("Maintainability", 0, 100);
        Metric MHF = new MHFMetric("MHF", 1, 0);
        Metric MIF = new MIFMetric("MIF", 0, 1);
        Metric NMA = new NMAMetric("NMA");
        Metric NMI = new NMIMetric("NMI");
        Metric NM = new NMMetric("NM");
        Metric NMO = new NMOMetric("NMO");
        Metric NOC = new NOCMetric("NOC");
        Metric NPV = new NPVMetric("NPV");
        Metric NV = new NVMetric("NV");
        Metric Operand = new OperandMetric("Operand");
        Metric Operator = new OperatorMetric("Operator");
        Metric PF = new PFMetric("PF");
        Metric PM = new PMMetric("PM");

        RefactorPageFactory defaultPageFactory = addPageFactory("Default", Arrays.asList(
                AHF, AIF, AMS, CLOC, Cyclomatic, DIT, DistinctOperand, DistinctOperator,
                HalsteadDifficulty, HalsteadEffort, HalsteadVolume, LLOC, LOC, Maintainability,
                MHF, MIF, NMA, NMI, NM, NMO, NOC, NPV, NV, Operand, Operator, PF, PM
        ), Arrays.asList(
                "MI", "OO"
        ));

        addPageFactory("MI", Arrays.asList(
                Maintainability, HalsteadDifficulty, HalsteadEffort, HalsteadVolume,
                Cyclomatic, DistinctOperand, DistinctOperator, Operand, Operator,
                CLOC, LOC, LLOC

        ), Arrays.asList(
                "back", "Halstead", "CC", "LOC"
        ));

        addPageFactory("Halstead", Arrays.asList(
                HalsteadDifficulty, HalsteadEffort, HalsteadVolume
        ), Arrays.asList(
                "back"
        ));

        addPageFactory("CC", Arrays.asList(
                Cyclomatic, DistinctOperand, DistinctOperator, Operand, Operator
        ), Arrays.asList(
                "back"
        ));

        addPageFactory("LOC", Arrays.asList(
                LLOC, CLOC, LOC
        ), Arrays.asList(
                "back"
        ));

        addPageFactory("OO", Arrays.asList(
                AHF, AIF, AMS, DIT, MHF, MIF, NMA, NMI, NM, NMO, NOC, NPV, NV, PF, PM
        ), Arrays.asList(
                "back"
        ));

        changeView("Default");
    }

    // put this to public?
    private RefactorPageFactory addPageFactory(String label, List<Metric> metrics, List<String> buttons) {
        RefactorPageFactory pf = new RefactorPageFactory(label, this, myToolWindowContent);
        for(Metric m: metrics) {
            pf.addMetric(m);
        }
        for(String b: buttons){
            pf.addButton(b);
        }

        pfMap.put(label, pf);
        return pf;
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JPanel getContent() {
        return myToolWindowContent;
    }

    @Override
    public void update(Project project, PsiElement elem) {
//        settingAllStatus();
        myToolWindowContent.revalidate();
    }

    public void changeView(String label) {
        if(label.equals("back")){
            history.pop();
            label = history.pop();
        }
        if(!pfMap.containsKey(label)) {
            if(currentPageFactory != null)
                uManager.removeObserver(currentPageFactory);
            currentPageFactory = pfMap.get("Default");
            uManager.clearObserver();
            history.clear();
            history.push("Default");
            uManager.addObserver(currentPageFactory);
            currentPageFactory.createPage();
            myToolWindowContent.revalidate();
            return;
        }

        if(currentPageFactory != null)
            uManager.removeObserver(currentPageFactory);
        currentPageFactory = pfMap.get(label);
        history.push(label);
        uManager.addObserver(currentPageFactory);
        currentPageFactory.createPage();
        myToolWindowContent.revalidate();
    }
}