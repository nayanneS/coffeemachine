package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;

import java.util.ArrayList;
import java.util.List;

import net.compor.frameworks.jcf.api.ComporFacade;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;



public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	private ComponentsFactory Factory;

	private int geralCentavos=0;
	private int[] troco = new int[6];
	private List<Coin> moedas;
	boolean condicao = true;
	private final int cafe = 35;

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.geralCentavos = 0;
		this.Factory = factory;
		this.Factory.getDisplay().info("Insert coins and select a drink!");
		this.moedas = new ArrayList<Coin>();
		this.addComponents();
	}
	@Override
	protected void addComponents() {
		this.add(new CoffeeBlack(this.Factory));
		this.add(new CoffeeBlackSugar(this.Factory));
		this.add(new CoffeeWhite(this.Factory));
		this.add(new CoffeeWhiteSugar(this.Factory));
	}
	
	public void insertCoin(Coin coin) {

		if (coin == null) {
			throw new CoffeeMachineException(" Moeda não aceita.");
		}
		this.moedas.add(coin);
		geralCentavos += coin.getValue();
		Factory.getDisplay().info("Total: US$ " + geralCentavos / 100 + "." + geralCentavos % 100);
	}

	public void cancel() {
		if (this.geralCentavos == 0) {
			throw new CoffeeMachineException("Não possui moedas inseridas");
		}
		this.Factory.getDisplay().warn(Messages.CANCEL);
		this.retirarMoedas();
	}
	private void limpaMoedas() {
		this.moedas.clear();
		}
	
	 public void retirarMoedas() {
		 for (Coin rev : Coin.reverse()) {
			 for (Coin aux : this.moedas) {
				 if (aux == rev) {
					 this.Factory.getCashBox().release(aux);
				 }
			 }
		}
			 		this.limpaMoedas();
			 		this.Factory.getDisplay().info(Messages.INSERT_COINS);
	}

	private int[] planejarTroco(int troco) throws CoffeeMachineException {
		int i = 0;
		for (Coin rev : Coin.reverse()) {
			if (rev.getValue() <= troco && Factory.getCashBox().count(rev) > 0) {
				while (rev.getValue() <= troco) {
					troco -= rev.getValue();
					this.troco[i]++;
				}
			}
					i++;
		}
		if (troco != 0) {
			throw new CoffeeMachineException("");
		}
		return this.troco;
	}
	private void DevolverCoins(int[] quantCoin) {
		for (int i = 0; i < quantCoin.length; i++) {
			int count = quantCoin[i];
			Coin coin = Coin.reverse()[i];
				for (int j = 1; j <= count; j++) {
					this.Factory.getCashBox().release(coin);
				}
		}
	}

	private int calcularTroco() {
		int count = 0;
			for (Coin rev : Coin.reverse()) {
				for (Coin aux : this.moedas) {
					if (aux == rev) {
						count += aux.getValue();
					}
				}
			}
		return count - this.cafe;
	}

	
		
	public void select(Drink drink) {
					    
		if (calcularTroco() < 0) {
			this.Factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			retirarMoedas();
			return;
		}
			condicao = (Boolean) requestService("verifyDrinkType", drink);
		if (!condicao) {
			retirarMoedas();
			return;
		}
		try {
			troco = planejarTroco(calcularTroco());
		}catch (Exception e) {
			this.Factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			retirarMoedas();
			return;
		}
			this.Factory.getDisplay().info(Messages.MIXING);
			requestService("selectDrinkType", drink);
			requestService("releaseDrink");
			DevolverCoins(troco);
			this.limpaMoedas();
			this.Factory.getDisplay().info(Messages.INSERT_COINS);
	}
		
		
	}
	


