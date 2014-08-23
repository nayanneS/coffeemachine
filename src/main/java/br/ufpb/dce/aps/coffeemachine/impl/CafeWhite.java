package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;


public class CafeWhite extends Cafes {
			
			
			protected Dispenser cupDispenser;
			protected Dispenser waterDispenser;
			protected Dispenser coffeePowderDispenser;
			protected Display display;
	
	public void instanciarDispenser(){
		cupDispenser = factory.getCupDispenser();
		waterDispenser = factory.getWaterDispenser();
		coffeePowderDispenser = factory.getCoffeePowderDispenser();
		display = factory.getDisplay();
	}
	
	public void prepararCafe(MyCoffeeMachine meucafe, ComponentsFactory factory){
			 
			if (meucafe.calculaTroco() < 0) {
				factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
				meucafe.retirarMoedas(factory);
				return;
			}
			
			
			factory.getCupDispenser().contains(1);
			factory.getWaterDispenser().contains(1);
			factory.getCoffeePowderDispenser().contains(1);
			factory.getCreamerDispenser().contains(1.2);
			
			if (!meucafe.planejamento(meucafe.calculaTroco())){
				factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
				meucafe.retirarMoedas(factory);
				factory.getDisplay().info(Messages.INSERT_COINS);
				return;
			}
			
			factory.getDisplay().info(Messages.MIXING);
			factory.getCoffeePowderDispenser().release(1.9);
			factory.getWaterDispenser().release(1.10);
			factory.getCreamerDispenser().release(1.8);
			
			factory.getDisplay().info(Messages.RELEASING);
			factory.getCupDispenser().release(1);
			factory.getDrinkDispenser().release(0.9);
			factory.getDisplay().info(Messages.TAKE_DRINK);
			
			meucafe.retornarTroco(meucafe.calculaTroco()); 
			
			factory.getDisplay().info(Messages.INSERT_COINS);
	}

}
