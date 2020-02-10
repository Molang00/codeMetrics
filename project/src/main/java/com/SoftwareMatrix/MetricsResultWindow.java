package com.SoftwareMatrix;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiElement;
import com.SoftwareMatrix.metrics.*;

import javax.swing.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.jetbrains.annotations.NotNull;

/**
 * Class for showing window, which contains the result of calculating metrics
 */
public class MetricsResultWindow implements UpdateObserver {
        /* Declare private fields here */
        private JPanel myToolWindowContent;
        private Map<String, RefactorPageFactory> pfMap;
        private List<Metric> metrics;

        private UpdateManager uManager;

        /**
         * Constructor of MetricsResultWindow This makes all metrics which our plugin
         * use, and add the metrics to appropriate page
         *
         * @param toolWindow window to add contents
         * @param project    the project which our plugin analyzes and shows about
         */
        public MetricsResultWindow(ToolWindow toolWindow, @NotNull Project project) {
                uManager = UpdateManager.getInstance(project); // init update manager
                myToolWindowContent = new JPanel();
                pfMap = new HashMap<>();

                Metric CLOC = new CLOCMetric("CLOC");
                Metric Cyclomatic = new CyclomaticMetric("Cyclomatic");
                Metric DistinctOperand = new DistinctOperandMetric("DistinctOperand");
                Metric DistinctOperator = new DistinctOperatorMetric("DistinctOperator");
                Metric HalsteadVolume = new HalsteadVolumeMetric("HalsteadVolume");
                Metric LLOC = new LLOCMetric("LLOC");
                Metric LOC = new LOCMetric("LOC");
                Metric Maintainability = new MaintainabilityMetric("Maintainability");
                Metric Operand = new OperandMetric("Operand");
                Metric Operator = new OperatorMetric("Operator");
                metrics = Arrays.asList(Maintainability, Operand, Operator, DistinctOperand, DistinctOperator,
                                HalsteadVolume, Cyclomatic, LOC, LLOC, CLOC);

                RefactorPageFactory defaultPageFactory = addPageFactory("Default",
                                Arrays.asList(Maintainability, Operand, Operator, DistinctOperand, DistinctOperator,
                                                HalsteadVolume, Cyclomatic, LOC, LLOC, CLOC));

                uManager.addObserver(defaultPageFactory);
                defaultPageFactory.createPage();
                myToolWindowContent.revalidate();
        }

        public void exitReport() throws IOException {
                double total = 0;
                double error = 0;
                String report = "";
                PrintWriter pw = new PrintWriter("out.txt");
                report += "이 코드는 일반적인 코드에 비해 ";
                for (Metric m : metrics) {
                        String sentance = m.generateReport();
                        report += sentance;
                        total++;
                        if (sentance.contains("매우"))
                                error++;
                }
                double errorPercent = error / total;
                if (errorPercent < 0.25) {
                        report += "유지 및 보수하기 좋은 구조로 되어 있습니다.\n";
                } else if (errorPercent < 0.75) {
                        report += "평범한 수준으로 개발되어 있습니다.\n";
                } else {
                        report += "전체적으로 수정이 요구됩니다.\n";
                }
                System.out.println(report);
        }

        /**
         * Makes new RefactorPageFactory with name label, adding metrics and buttons to
         * page.
         *
         * @param label   The name of page
         * @param metrics Metrics which are added to page
         * @param buttons Buttons which are added to page
         * @return RefactorPageFactory with label, containing metrics and buttons
         */
        private RefactorPageFactory addPageFactory(String label, List<Metric> metrics) {
                RefactorPageFactory pf = new RefactorPageFactory(label, this, myToolWindowContent);
                for (Metric m : metrics) {
                        pf.addMetric(m);
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

        /**
         * Update the MetricsResultWindow, calling revalidate of window content
         *
         * @param project the project which our plugin analyzes and shows about
         * @param elem    PsiElement which makes event
         */
        @Override
        public void update(Project project, PsiElement elem) {
                // settingAllStatus();
                myToolWindowContent.revalidate();
        }
}