package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;
import java.util.List;

import net.compor.frameworks.jcf.api.ComporFacade;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;
import static org.mockito.Mockito.verify;


public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	private ComponentsFactory factory;
	private ArrayList<Coin> moedas;
	private int[] troco = new int[6];
	private int totalcentavos = 0;
	boolean condicao = true;
	private int CAFE = 35;
	private String modo = "";
	private boolean lerMoeda = false, lerCracha = false;
	

	@Override
	protected void addComponents() {

		this.add(new CoffeeBlack(this.factory));
		this.add(new CoffeeBlackSugar(this.factory));
		this.add(new CoffeeWhite(this.factory));
		this.add(new CoffeeWhiteSugar(this.factory));
		this.add(new CaldoDeSopaBoullion(this.factory));
	}

	public void insertCoin(Coin coin) {
		if (!this.lerCracha){
			this.lerMoeda=true;
			if (coin == null){
				throw new CoffeeMachineException("");
			}
		
		if (this.modo.equals("cracha")) {
			factory.getDisplay().warn(Messages.CAN_NOT_INSERT_COINS);
			liberarMoedasCracha(factory, coin);
			return;
		}
		else{
			
		}
		if (coin == null) {
			throw new CoffeeMachineException("Moeda Não Aceita");
		}
		this.moedas.add(coin);
		totalcentavos += coin.getValue();
		factory.getDisplay()
				.info("Total: US$ " + totalcentavos / 100 + "." + totalcentavos
						% 100);
	}
	}
	private void liberarMoedasCracha(ComponentsFactory factory, Coin coin) {
		this.factory.getCashBox().release(coin);
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public void cancel() {
		if (this.totalcentavos == 0) {
			throw new CoffeeMachineException("Não possui moedas inseridas");
		}
		this.factory.getDisplay().warn(Messages.CANCEL);
		this.removerCoins();
	}

	private void limpaMoedas() {
		this.moedas.clear();
	}

	private void removerCoins() {
		for (Coin rev : Coin.reverse()) {
			for (Coin aux : this.moedas) {
				if (aux == rev) {
					this.factory.getCashBox().release(aux);
				}
			}
		}
		this.limpaMoedas();
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}

	private int[] planejarTroco(int troco) throws CoffeeMachineException {
		int i = 0;
		for (Coin rev : Coin.reverse()) {
			if (rev.getValue() <= troco && factory.getCashBox().count(rev) > 0) {
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

	private void releaseCoins(int[] quantCoin) {
		for (int i = 0; i < quantCoin.length; i++) {
			int count = quantCoin[i];
			Coin coin = Coin.reverse()[i];

			for (int j = 1; j <= count; j++) {
				this.factory.getCashBox().release(coin);
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
		return count - this.CAFE;
	}

	public void select(Drink drink) {
		if (drink.equals(Drink.BOUILLON)) {
			this.CAFE = 25;
		}
		if (calcularTroco() < 0) {
			this.factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			removerCoins();
			return;
		}
		condicao = (Boolean) requestService("verifyDrinkType", drink);
		if (!condicao) {
			removerCoins();
			return;
		}

		try {
			troco = planejarTroco(calcularTroco());
		} catch (Exception e) {
			this.factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			removerCoins();
			return;
		}
		this.factory.getDisplay().info(Messages.MIXING);
		requestService("selectDrinkType", drink);
		requestService("releaseDrink");
		releaseCoins(troco);

		this.limpaMoedas();
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public void setFactory(ComponentsFactory factory) {
		this.factory = factory;
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		this.totalcentavos = 0;
		this.moedas = new ArrayList<Coin>();
		this.addComponents();
	}

	public void readBadge(int badgeCode) {
		if (!this.lerMoeda){
			this.factory.getDisplay().info(Messages.BADGE_READ);
			this.setModo("cracha");
		}
		else{
			this.factory.getDisplay().warn(Messages.CAN_NOT_READ_BADGE);

		}

	}
	
}
	


