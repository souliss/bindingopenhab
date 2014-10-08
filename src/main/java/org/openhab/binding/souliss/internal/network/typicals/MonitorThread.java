package org.openhab.binding.souliss.internal.network.typicals;



import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.openhab.binding.souliss.internal.SoulissUpdater;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.StringType;




public class MonitorThread extends Thread {

	int REFRESH_TIME;
	private SoulissTypicals soulissTypicalsRecipients;
	final Logger LOGGER = Logger.getLogger(Constants.LOGNAME);	//SoulissUpdater sUpdater=new SoulissUpdater ();
	 EventPublisher eventPublisher;
		
	public MonitorThread(SoulissTypicals typicals, int iRefreshTime, EventPublisher _eventPublisher) {
		// TODO Auto-generated constructor stub
		REFRESH_TIME=iRefreshTime;
		soulissTypicalsRecipients=typicals;
		LOGGER.info("Avvio MonitorThread");
		eventPublisher=_eventPublisher;
	}


	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				//SCORRE LA LISTA DEI TIPICI RILEVANDO SOLO QUELLI AGGIORNATI
				check(soulissTypicalsRecipients);
								
				Thread.sleep(REFRESH_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				LOGGER.severe(e.getMessage());
			}
			super.run();
		}
	}


	private void check(SoulissTypicals typicals) {
		// TODO Auto-generated method stub
		Iterator<Entry<String, SoulissGenericTypical>> iteratorTypicals= soulissTypicalsRecipients.getIterator();
		synchronized (iteratorTypicals) {
			while (iteratorTypicals.hasNext()){
				SoulissGenericTypical typ= iteratorTypicals.next().getValue();
				if(typ.isUpdated()){
					//QUI SI LANCIANO I METODI OPENHAB PER L'AGGIORNAMENTO DEGLI ITEM
					//	qui bisogna mandare gli aggiornamenti sul bus OH con il comando
					//	eventPublisher.postUpdate(itemName, state)
//**CREARE GLI STATI IN MODO CORRETTO
//COSI' NON VENGONO ACCETTATI
					if (typ.getType()==Constants.Souliss_TService_NODE_TIMESTAMP){
						//solo il valore TIMESTAMP si differenzia da tutti gli altri perchè nella classe SoulissTServiceNODE_TIMESTAMP il valore è rappresentato come stringa mentre i valori classici son rappr.come float
						LOGGER.fine("Put on Bus Events - itemName=newState : " + typ.getName() + " = " + ((SoulissTServiceNODE_TIMESTAMP) typ).getTIMESTAMP() );
						//eventPublisher.postUpdate(typ.getName(),  new StringType(((SoulissTServiceNODE_TIMESTAMP) typ).getTIMESTAMP()));
						
					//	EventBus.putOnOHEventBus(typ.getName(), ((SoulissTServiceNODE_TIMESTAMP) typ).getTIMESTAMP());
					}else {//if (typ.getNote().equals("SwitchItem")){
						//fare lo StateTraslate
						LOGGER.fine("Put on Bus Events - itemName=newState : " + typ.getName() + " = " + Float.toString(typ.getState()) );
						eventPublisher.postUpdate(typ.getName(),  typ.getOHState());
					}

					typ.resetUpdate();
				}
			}
		}

	}
}

