package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;

import java.util.ArrayList;
import java.util.List;

import net.compor.frameworks.jcf.api.ComporFacade;
import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Dispenser;


public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	private ComponentsFactory Factory;
	private int inteiro;
	private int centavos;
	private CashBox cb;
	private List<Coin> lista, ltroco;
	private Drink bebida;
	private final int cafe = 35;

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.centavos = 0;
		this.inteiro = 0;
		this.Factory = factory;
		this.Factory.getDisplay().info("Insert coins and select a drink!");
		this.cb = Factory.getCashBox();
		this.lista = new ArrayList<Coin>();
		this.ltroco = new ArrayList<Coin>();
	}

	public void insertCoin(Coin coin) {

		if (coin == null) {
			throw new CoffeeMachineException("Moeda inválida.");
		}
		inteiro += coin.getValue() / 100;
		centavos += coin.getValue() % 100;
		this.Factory.getDisplay().info(
				"Total: US$" + " " + inteiro + "." + centavos);
		this.lista.add(coin);
	}

	public void cancel() {
		if (this.inteiro == 0 && this.centavos == 0) {
			throw new CoffeeMachineException("Ausencia de moedas.");
		}

		this.Factory.getDisplay().warn(Messages.CANCEL);

		retirarMoedas(Factory);

		this.Factory.getDisplay().info(Messages.INSERT_COINS);

		this.lista.clear();
	}
	
	 void retirarMoedas(ComponentsFactory factory) {
		List<Integer> remover = new ArrayList<Integer>();

		for (Coin coin : Coin.reverse()) {
			for (int i = 0; i < this.lista.size(); i++) {
				if (this.lista.get(i).equals(coin)) {
					this.Factory.getCashBox().release(lista.get(i));
					remover.add(new Integer(i));
				}
			}
		}
		this.lista.removeAll(remover);
	 }
	
	
	public List<Coin> retornarTroco(int troco) {
		for (Coin coin : Coin.reverse()) {
			while (coin.getValue() <= troco) {
				Factory.getCashBox().release(coin);
				this.ltroco.add(coin);
				troco -= coin.getValue();
			}
		}
		return ltroco;
	}
	

	public boolean planejamento(int troco) {
		for (Coin coin : Coin.reverse()) {
			if (coin.getValue() <= troco
					&& this.Factory.getCashBox().count(coin) > 0) {
				troco -= coin.getValue();
			}

		}
		return troco == 0;

	}

	public int calculaTroco() {
		int contador = 0;
		for (Coin c : this.lista) {
			contador += c.getValue();
		}
		return contador - this.cafe;

	}

	
		
	public void select(Drink drink) {
					
		Cafes cafe = null;
			
	switch (drink) {
					
		case BLACK:
		cafe = new CafePreto();
		break;

		case BLACK_SUGAR:
		cafe = new CafePretoSugar();
		break;
		
		case WHITE:
		cafe = new CafeWhite();
		break;
		
		
		case WHITE_SUGAR:
		cafe = new CafeWhiteSugar();
		
	}
		
		cafe.prepararCafe(this, Factory);
		lista.clear();
	}
	
}

