package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class CafePretoSugar extends Cafes {
	
			protected ComponentsFactory factory;
			protected Dispenser cupDispenser;
			protected Dispenser waterDispenser;
			protected Dispenser coffeePowderDispenser;
			protected Dispenser SugarDispenser;
			protected Display display;
	
			
	public void instanciarDispenser(){
		cupDispenser = factory.getCupDispenser();
		waterDispenser = factory.getWaterDispenser();
		coffeePowderDispenser = factory.getCoffeePowderDispenser();
		SugarDispenser= factory.getSugarDispenser();
		display = factory.getDisplay();
	}
	
	public void prepararCafe(MyCoffeeMachine meucafe, ComponentsFactory fac){
		
		if (meucafe.calculaTroco() < 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			meucafe.retirarMoedas(fac);
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
			factory.getCoffeePowderDispenser().contains(2.3);
			
		if (!factory.getSugarDispenser().contains(1.2)) {
			factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
			meucafe.retirarMoedas(fac);
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
		factory.getCoffeePowderDispenser().release(1.2);
		factory.getWaterDispenser().release(2.2);
		factory.getSugarDispenser().release(3.2);
		
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
		factory.getDrinkDispenser().release(2.2);
		factory.getDisplay().info(Messages.TAKE_DRINK);
		
		meucafe.retornarTroco(meucafe.calculaTroco()); 
		
		factory.getDisplay().info(Messages.INSERT_COINS);
		
	
	}
}
