package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import net.compor.frameworks.jcf.api.Service;

public class CaldoDeSopaBoullion extends CoffeeBlack {
	
	public CaldoDeSopaBoullion(ComponentsFactory factory) {
			super(factory);
		}
	
	@Service
	public boolean VerificaDrinkBouillon(){
			if (!this.factory.getCupDispenser().contains(1)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_CUP);
				return false;
			}
			if (!this.factory.getWaterDispenser().contains(100)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_WATER);
				return false;
			}
				this.factory.getBouillonDispenser().contains(10);
				return true;
	}
	@Service
	public void releaseBouillon(){
		this.factory.getBouillonDispenser().release(10);
		this.factory.getWaterDispenser().release(100);
	}

}
