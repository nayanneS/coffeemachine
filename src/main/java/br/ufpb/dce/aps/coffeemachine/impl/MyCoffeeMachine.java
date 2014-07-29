package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;

import java.util.ArrayList;
import java.util.List;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	private ComponentsFactory Factory;
	private int inteiro;
	private int centavos;
	private CashBox cb;
	private List<Coin> lista;
	private Drink bebida;

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.centavos = 0;
		this.inteiro = 0;
		this.Factory = factory;
		this.Factory.getDisplay().info("Insert coins and select a drink!");
		this.cb = Factory.getCashBox();
		this.lista = new ArrayList<Coin>();
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

		retirarmoedas(Factory);

		this.Factory.getDisplay().info(Messages.INSERT_COINS);

	}

	private void retirarmoedas(ComponentsFactory factory) {
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

	public void select(Drink drink) {

		switch (drink) {
		case BLACK:

			Factory.getCupDispenser().contains(1);
			Factory.getWaterDispenser().contains(1.5);
			if (!Factory.getCoffeePowderDispenser().contains(0.1)) {

				Factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
				retirarmoedas(Factory);
				this.Factory.getDisplay().info(Messages.INSERT_COINS);
				return;
			}

			Factory.getDisplay().info(Messages.MIXING);
			Factory.getCoffeePowderDispenser().release(1.2);
			Factory.getWaterDispenser().release(1.0);

			Factory.getDisplay().info(Messages.RELEASING);
			Factory.getCupDispenser().release(1);
			Factory.getDrinkDispenser().release(1.4);
			Factory.getDisplay().info(Messages.TAKE_DRINK);

			Factory.getDisplay().info(Messages.INSERT_COINS);

			break;

		case BLACK_SUGAR:

			Factory.getCupDispenser().contains(1);
			Factory.getWaterDispenser().contains(1.6);
			if (Factory.getCoffeePowderDispenser().contains(1) == false) {
				Factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
				retirarmoedas(Factory);
				this.Factory.getDisplay().info(Messages.INSERT_COINS);
				return;
			}
			if( !Factory.getSugarDispenser().contains(1)){
				Factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				retirarmoedas(Factory);
				this.Factory.getDisplay().info(Messages.INSERT_COINS);
				return;
				
			}

			Factory.getDisplay().info(Messages.MIXING);
			Factory.getCoffeePowderDispenser().release(1.9);
			Factory.getWaterDispenser().release(1.10);
			Factory.getSugarDispenser().release(1.11);

			Factory.getDisplay().info(Messages.RELEASING);
			Factory.getCupDispenser().release(1);
			Factory.getDrinkDispenser().release(0.9);
			Factory.getDisplay().info(Messages.TAKE_DRINK);

			Factory.getDisplay().info(Messages.INSERT_COINS);

			this.lista.clear();
		}

	}

}
