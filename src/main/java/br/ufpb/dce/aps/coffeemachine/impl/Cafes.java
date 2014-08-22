package br.ufpb.dce.aps.coffeemachine.impl;

import java.awt.Component;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

import net.compor.frameworks.jcf.api.Service;

public abstract  class Cafes {
	
	
	protected ComponentsFactory factory;
	
	public void instanciarDispenser(){}
	
	public void prepararCafe(MyCoffeeMachine meucafe, ComponentsFactory fac){}
	
}
