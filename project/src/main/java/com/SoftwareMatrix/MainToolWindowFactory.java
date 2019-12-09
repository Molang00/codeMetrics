package com.SoftwareMatrix;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * A factory class to create a Metrics Result Table tool window
 */
public class MainToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        MetricsResultWindow psw = new MetricsResultWindow(toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(psw.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);


        UpdateManager uManager = UpdateManager.getInstance(project); // init update manager
        uManager.addObserver(psw);
    }
}
