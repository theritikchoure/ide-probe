package org.virtuslab.ideprobe

import org.virtuslab.ideprobe.ProbeHandlers.ProbeHandler
import org.virtuslab.ideprobe.handlers._
import org.virtuslab.ideprobe.protocol.Endpoints

class BaseProbeHandlerContributor extends ProbeHandlerContributor {

  override def registerHandlers(handler: ProbeHandler): ProbeHandler = {
    handler
      .on(Endpoints.SetConfig)(ProbeConfig.initialize)
      .on(Endpoints.PreconfigureJdk)(_ => JDK.preconfigure())
      .on(Endpoints.PID)(_ => App.pid)
      .on(Endpoints.SystemProperties)(_ => App.systemProperties)
      .on(Endpoints.Ping)(_ => ())
      .on(Endpoints.Plugins)(_ => Plugins.list)
      .on(Endpoints.Shutdown)(_ => App.shutdown())
      .on(Endpoints.Messages)(_ => IdeMessages.list.toIndexedSeq)
      .on(Endpoints.Freezes)(_ => Freezes.list)
      .on(Endpoints.InvokeAction)(Actions.invokeSync)
      .on(Endpoints.InvokeActionAsync)(Actions.invokeAsync)
      .on(Endpoints.FileReferences)(PSI.references)
      .on(Endpoints.Find)(Navigation.find)
      .on(Endpoints.OpenProject)(Projects.open)
      .on(Endpoints.RefreshAllExternalProjects)(Projects.refreshAll)
      .on(Endpoints.CloseProject)(Projects.close)
      .on(Endpoints.SetCompilerOutput)((Projects.setCompilerOutput _).tupled)
      .on(Endpoints.ProjectModel)(Projects.model)
      .on(Endpoints.ProjectSdk)(Projects.sdk)
      .on(Endpoints.ListOpenProjects)(_ => Projects.all)
      .on(Endpoints.ModuleSdk)(Modules.sdk)
      .on(Endpoints.BackgroundTasks)(_ => BackgroundTasks.currentBackgroundTasks())
      .on(Endpoints.Build)(Builds.build)
      .on(Endpoints.SyncFiles)(_ => VFS.syncAll())
      .on(Endpoints.AwaitNotification)((Notifications.await _).tupled)
      .on(Endpoints.RunApp)(RunConfigurations.runApp)
      .on(Endpoints.RunJUnit)(RunConfigurations.runJUnit)
      .on(Endpoints.RerunFailedTests)(RunConfigurations.rerunFailedTests)
      .on(Endpoints.TestConfigurations)(RunConfigurations.testConfigurations)
      .on(Endpoints.RunTestsFromGenerated)((RunConfigurations.runTestsFromGenerated _).tupled)
      .on(Endpoints.TakeScreenshot)(Screenshot.take)
      .on(Endpoints.RunLocalInspection)(Inspections.runLocal)
      .on(Endpoints.VcsRoots)(VCS.roots)
      .on(Endpoints.ExpandMacro)(ExpandMacro.expand)
      .on(Endpoints.BuildArtifact)((Builds.buildArtifact _).tupled)
      .on(Endpoints.OpenEditor)(Editors.open)
      .on(Endpoints.GoToLineColumn)((Editors.goToLineColumn _).tupled)
      .on(Endpoints.ListOpenEditors)(Editors.all)
      .on(Endpoints.AddTrustedPath)(TrustedPaths.add)
      .on(Endpoints.HighlightInfo)(Highlighting.infos)
  }
}
