package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	public MyCoffeeMachine(ComponentsFactory factory) {
		// TODO Auto-generated constructor stub
		factory.getDisplay().info("Insert coins and select a drink!");
		
	}

	
}
