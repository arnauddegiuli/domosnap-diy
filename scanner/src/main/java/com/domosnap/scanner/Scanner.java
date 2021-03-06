package com.domosnap.scanner;

/*
 * #%L
 * OpenWebNetScanner
 * %%
 * Copyright (C) 2017 - 2021 A. de Giuli
 * %%
 * This file is part of HomeSnap done by A. de Giuli (arnaud.degiuli(at)free.fr).
 * 
 *     MyDomo is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     MyDomo is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with MyDomo.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.text.MessageFormat;
import java.util.List;

import com.domosnap.engine.adapter.controller.UnknownControllerListener;
import com.domosnap.engine.adapter.impl.openwebnet.OpenWebNetControllerService;
import com.domosnap.engine.controller.Command;
import com.domosnap.engine.controller.what.What;


public class Scanner {

	
	private Scanner(String uri) {
		
		OpenWebNetControllerService o = new OpenWebNetControllerService(uri);
		o.addUnknowControllerListener(new UnknownControllerListener() {

			@Override
			public void foundUnknownController(Command command) {
				List<What> whatList = command.getWhatList();
				for (What what : whatList) {
					String whereStr = command.getWhere()!= null ? command.getWhere().getUri() : "null";
					String whatStr = what == null ? "null": what.getName();
					String valueStr = what == null ? "null": what.getValue() == null ? "null" : what.getValue().toString();
					System.out.println(MessageFormat.format("Who [{0}] : Where [{1}] : what [{2}] : value [{3}]\n", command.getWho(), whereStr, whatStr, valueStr));
				}
			}
		});
		o.connect();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Scanner("12345@192.168.1.35:20000");
	}
}
