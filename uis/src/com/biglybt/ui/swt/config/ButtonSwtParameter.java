/*
 * Created on 17-Jun-2004
 * Created by Paul Gardner
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package com.biglybt.ui.swt.config;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.biglybt.pifimpl.local.ui.config.ActionParameterImpl;
import com.biglybt.pifimpl.local.ui.config.ParameterImpl;
import com.biglybt.ui.swt.Messages;
import com.biglybt.ui.swt.Utils;
import com.biglybt.ui.swt.widgets.ButtonWithMinWidth;

import com.biglybt.pif.ui.config.ActionParameter;

public class ButtonSwtParameter
	extends BaseSwtParameter<ButtonSwtParameter, Object>
{
	final Button button;

	public ButtonSwtParameter(Composite parent, ActionParameterImpl pluginParam) {
		this(parent, pluginParam.getActionResource(), pluginParam.getLabelKey());
		setPluginParameter(pluginParam);
	}

	/**
	 * Make a button.
	 * <p/>
	 * When parent is of GridLayout, resulting new widgets will take 2 columns
	 *
	 * @param composite Where widgets will be placed. Parent is not altered
	 * @param buttonTextKey Messagebundle key text displayed in button
	 * @param labelKey Messagebundle key for the checkbox
	 */
	public ButtonSwtParameter(Composite composite, String buttonTextKey,
			String labelKey) {
		super(null);

		createStandardLabel(composite, labelKey);

		button = new ButtonWithMinWidth(composite, SWT.PUSH, 40);
		setMainControl(button);

		Messages.setLanguageText(button, buttonTextKey);

		button.addListener(SWT.Selection, event -> triggerChangeListeners(true));
	}

	@Override
	protected void triggerSubClassChangeListeners() {
		if (pluginParam instanceof ParameterImpl) {
			((ParameterImpl) pluginParam).fireParameterChanged();
		}
	}

	@Override
	public void refreshControl() {
		super.refreshControl();
		if (pluginParam instanceof ActionParameter) {

			Utils.execSWTThread(() -> {
				if (button.isDisposed()) {
					return;
				}

				Messages.updateLanguageKey(button,
						((ActionParameter) pluginParam).getActionResource());
			});
		}
	}
}