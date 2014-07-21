package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	
	private ComponentsFactory Factory;
	private int inteiro;
	private int centavos;
	private CashBox cb;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.centavos=0;
		this.inteiro=0;
		this.Factory = factory;
		this.Factory.getDisplay().info("Insert coins and select a drink!");
		this.cb = Factory.getCashBox(); 
	}

	public void insertCoin(Coin coin) {
		
		if (coin == null){
			throw new CoffeeMachineException("Moeda inválida.");
		}
		 inteiro += coin.getValue()/100;
		 centavos += coin.getValue()%100;
		
		 
		 this.Factory.getDisplay().info("Total: US$"+" "+ inteiro+"."+ centavos); 
		
		
	}

	public void cancel() {
		if(this.inteiro == 0 && this.centavos== 0){
			throw new CoffeeMachineException("Ausencia de moedas.");
		}
		
	
		this.Factory.getDisplay().warn(Messages.CANCEL_MESSAGE);
		cb.release(Coin.halfDollar);
		this.Factory.getDisplay().info(Messages.INSERT_COINS_MESSAGE);
	}
	
	
	
		
	
}
