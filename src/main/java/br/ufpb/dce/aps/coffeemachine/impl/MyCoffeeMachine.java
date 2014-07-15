package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	private int totalcents= 0;
	private ComponentsFactory Factory;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.Factory = factory;
		this.Factory.getDisplay().info("Insert coins and select a drink!");
		
	}

	public void insertCoin(Coin coin) {
		// TODO Auto-generated method stub
		 totalcents += coin.getValue();
		 int inteiro = totalcents/100;
		 int centavos =totalcents%100;
		
		 
		 this.Factory.getDisplay().info("Total: US$"+" "+ inteiro+"."+ centavos); 
		
		
	}
	
	
		
	
}
