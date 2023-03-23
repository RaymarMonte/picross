package picross.models.app;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import picross.I18N;
import picross.models.session.Session;

import java.util.Locale;

public class App {

	private final Session session;
	private ObjectProperty<AppColor> color;

	public App() {
		color = new SimpleObjectProperty<>(AppColor.GREEN);
		session = new Session();
	}

	public Session getSession() {
		return session;
	}

	public AppColor getColor() {
		return color.get();
	}

	public void setColor(AppColor color) {
		this.color.set(color);
	}

	public ObjectProperty<AppColor> colorProperty() {
		return color;
	}

	public void setLocale(Locale locale) {
		if (I18N.getLocale() != locale) {
			I18N.setLocale(locale);
		}
	}

	public Object getSessionToSave() {
		return session;
	}

	public void loadSession(Object session) {
		Session loadedSession = (Session) session;
		this.session.load(loadedSession);
	}
}
