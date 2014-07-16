package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	
	private ComponentsFactory Factory;
	private int inteiro;
	private int centavos;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.centavos=0;
		this.inteiro=0;
		this.Factory = factory;
		this.Factory.getDisplay().info("Insert coins and select a drink!");
		
	}

	public void insertCoin(Coin coin) {
		
		if (coin == null){
			throw new CoffeeMachineException("Moeda inválida.");
		}
		 inteiro += coin.getValue()/100;
		 centavos += coin.getValue()%100;
		
		 
		 this.Factory.getDisplay().info("Total: US$"+" "+ inteiro+"."+ centavos); 
		
		
	}
	
	
	
		
	
}
