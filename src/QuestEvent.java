import javafx.event.*;

// Not used yet
public class QuestEvent extends Event {
	public static final EventType<QuestEvent> QUEST_ISSUED = new EventType<QuestEvent>("QUEST_ISSUED");
	public static final EventType<QuestEvent> QUEST_COMPLETED = new EventType<QuestEvent>("QUEST_COMPLETED");
	Quest quest;

	QuestEvent(Quest quest, EventType<QuestEvent> e) {
		super(e);
		this.quest = quest;
	}
}
