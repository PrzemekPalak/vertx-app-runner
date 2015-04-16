package pl.finsys.vertx;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import java.util.Map;

/**
 * Module allows You to run vertx application consisting many vertx modules using one configuration file.
 *
 * @author Przemek Palak
 */
public class AppRunner extends BusModBase {

	public static final String RUNNING_MODULE_MSG = "Running module %s in %d instances with configuration: %s ";
	public static final String SETTING_SYSTEM_MSG = "Setting system property for key %s : %s";

	public void start() {

		container.logger().info("Running Application");

		//set system properties
		if (getContainer().config().containsField("systemProperties")) {
			JsonObject systemProperties = getContainer().config().getObject("systemProperties");

			for (Map.Entry<String, Object> property : systemProperties.toMap().entrySet()) {
				String key = property.getKey();
				String value = (String) property.getValue();
				container.logger().info(String.format(SETTING_SYSTEM_MSG, key, value));
				System.setProperty(key, value);
			}

		}
		if (getContainer().config().containsField("mods")) {

			//run modules
			JsonArray mods = getContainer().config().getArray("mods");
			for (Object modObj : mods.toList()) {

				Map<String, Object> mod = (Map<String, Object>) modObj;
				String moduleName = (String) mod.get("name");
				Integer instances = (Integer) mod.get("instances");

				if (instances > 0) {
					JsonObject conf = new JsonObject((Map) mod.get("conf"));
					container.logger().info(String.format(RUNNING_MODULE_MSG, moduleName, instances, conf.encodePrettily()));
					container.deployModule(moduleName, conf, instances);
				}

			}
		}
	}
}
