package org.openhab.binding.souliss.internal;

import java.util.Dictionary;

import org.openhab.binding.souliss.SoulissBindingProvider;
import org.openhab.core.binding.AbstractBinding;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.library.types.StringType;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

public class SoulissUpdater extends AbstractBinding<SoulissBindingProvider>  implements ManagedService {

	 protected EventPublisher eventPublisher = null;
	
	@Override
	public void setEventPublisher(EventPublisher eventPublisher) {
		// TODO Auto-generated method stub
		super.setEventPublisher(eventPublisher);
	}

	@Override
	public void unsetEventPublisher(EventPublisher eventPublisher) {
		// TODO Auto-generated method stub
		super.unsetEventPublisher(eventPublisher);
	}

	public void update(String itemName, String newState) {
		eventPublisher.postUpdate(itemName, new StringType(newState));
	}

	@Override
	public void updated(Dictionary<String, ?> properties)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		
	}

	

}
