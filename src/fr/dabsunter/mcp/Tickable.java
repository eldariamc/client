package fr.dabsunter.mcp;

/**
 * Created by David on 20/12/2016.
 */
public interface Tickable {

	void onTick(Phase phase);

	enum Phase {
		START,
		END
	}

}
