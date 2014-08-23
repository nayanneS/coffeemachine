package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class CafePreto extends Cafes {

	
		protected Dispenser cupDispenser;
		protected Dispenser waterDispenser;
		protected Dispenser coffeePowderDispenser;
		protected DrinkDispenser drinkDispenser;
		protected Display display;
	
	
	public void instanciarDispenser(){
		cupDispenser = factory.getCupDispenser();
		waterDispenser = factory.getWaterDispenser();
		coffeePowderDispenser = factory.getCoffeePowderDispenser();
		drinkDispenser = factory.getDrinkDispenser();
		display = factory.getDisplay();
	}
	
	public void prepararCafe(MyCoffeeMachine meucafe, ComponentsFactory factory){
		if (meucafe.calculaTroco() < 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			meucafe.retirarMoedas(factory);
			factory.getDisplay().info(Messages.INSERT_COINS);
			return;
		}
		
		if (!factory.getCupDispenser().contains(1)) {
			factory.getDisplay().warn(Messages.OUT_OF_CUP);
			meucafe.retirarMoedas(factory);
			factory.getDisplay().info(Messages.INSERT_COINS);
			return;
		}
		
		if (!factory.getWaterDispenser().contains(1.2)) {
			factory.getDisplay().warn(Messages.OUT_OF_WATER);
			meucafe.retirarMoedas(factory);
			factory.getDisplay().info(Messages.INSERT_COINS);
			return;
		}
		
		if (!factory.getCoffeePowderDispenser().contains(1.2)) {
			factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			meucafe.retirarMoedas(factory);
			factory.getDisplay().info(Messages.INSERT_COINS);
			return;
		}
		if (!meucafe.planejamento(meucafe.calculaTroco())){
			factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			meucafe.retirarMoedas(factory);
			this.factory.getDisplay().info(Messages.INSERT_COINS);
			return;
			}
		
		
		factory.getDisplay().info(Messages.MIXING);
		factory.getCoffeePowderDispenser().release(1.5);
		factory.getWaterDispenser().release(2.5);
		
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
		factory.getDrinkDispenser().release(1);
		factory.getDisplay().info(Messages.TAKE_DRINK);
		
		meucafe.retornarTroco(meucafe.calculaTroco()); 
		
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

}

