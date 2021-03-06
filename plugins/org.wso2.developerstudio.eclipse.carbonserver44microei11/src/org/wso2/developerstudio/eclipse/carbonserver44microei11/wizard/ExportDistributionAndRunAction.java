/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.

 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at

 *      http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.developerstudio.eclipse.carbonserver44microei11.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.wso2.developerstudio.eclipse.carbonserver44microei11.Activator;
import org.wso2.developerstudio.eclipse.carbonserver44microei11.register.product.servers.MicroIntegratorInstance;
import org.wso2.developerstudio.eclipse.logging.core.IDeveloperStudioLog;
import org.wso2.developerstudio.eclipse.logging.core.Logger;

public class ExportDistributionAndRunAction implements IActionDelegate {

	IStructuredSelection selection;
	private static IDeveloperStudioLog log = Logger.getLog(Activator.PLUGIN_ID);

	public void run(IAction action) {
		if (selection != null) {
			CompositeApplicationArtifactUpdateWizard wizard = new CompositeApplicationArtifactUpdateWizard();
			wizard.init(PlatformUI.getWorkbench(), selection);
			WizardDialog exportWizardDialog = new WizardDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);

			int returnCode = exportWizardDialog.open();
			if (returnCode == Window.OK) {
				// restart internal micro-integrator profile
				try {
					MicroIntegratorInstance.getInstance().restart();
				} catch (CoreException e) {
					log.error("Error occured while restarting the micro-integrator", e);
				}
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}

	}

}
