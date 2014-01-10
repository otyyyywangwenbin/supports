package test;

import java.io.File;

import com.eos.common.connection.mbean.DataSourceConfigModel.C3p0DataSourceItem;
import com.eos.common.transaction.ITransactionManager;
import com.eos.common.transaction.mbean.TxManagerConfigModel.TxManagerConfigGroup;
import com.eos.das.entity.mbean.DASConfigHandler;
import com.eos.das.entity.mbean.DASConfigModel;
import com.eos.infra.config.Configuration;
import com.eos.infra.config.Configuration.Module;
import com.eos.runtime.core.ApplicationContext;
import com.eos.runtime.core.TraceLoggerFactory;
import com.primeton.common.transaction.impl.datasource.DataSourceTransactionManagerSetProvider;
import com.primeton.ext.common.connection.datasource.DataSourceCache;
import com.primeton.ext.common.connection.datasource.DataSourceFactory;
import com.primeton.ext.common.l7e.ImprimaturContext;
import com.primeton.ext.common.transaction.ITransactionManagerProvider;
import com.primeton.ext.common.transaction.TransactionManagerCacher;
import com.primeton.ext.system.embedded.EmbeddedConfig;
import com.primeton.ext.system.embedded.EmbeddedSystemCache;

public class Initer {
	private static final String APP_NAME = "test";

	private static File EOS_HOME = null;

	private static File APP_HOME = null;

	public static void main(String[] args) {
		init();
	}

	public static void init() {
		initENV();
		initLicense();
		initLog();
		initDataSource();
		initTransactionManager();
		initDAS();
	}

	private static void initENV() {
		File dir = new File(Initer.class.getResource("/").getFile());
		EOS_HOME = new File(dir, "../eos_home");
		APP_HOME = new File(EOS_HOME, APP_NAME);
		EmbeddedConfig emconf = new EmbeddedConfig();
		emconf.setServerHome(EOS_HOME.getAbsolutePath());
		emconf.setServerWorkingDir(new File(APP_HOME, "work_temp").getAbsolutePath());
		emconf.setExternalConfigDir(EOS_HOME.getAbsolutePath());
		emconf.setAppName(APP_NAME);
		EmbeddedSystemCache.initOnlyOnce(true, emconf);
		ApplicationContext.getInstance().setAppName(APP_NAME);
	}

	private static void initLicense() {
		ImprimaturContext.getInstance().setImprimaturFilePath(new File(APP_HOME, "license/primetonlicense.xml").getAbsolutePath());
	}

	private static void initLog() {
		TraceLoggerFactory.register(new File(APP_HOME, "config/log4j-trace.xml").getAbsolutePath());
	}

	private static void initDataSource() {
		// C3p0DataSourceItem mysql = new C3p0DataSourceItem();
		// mysql.setC3p0DriverClass("com.mysql.jdbc.Driver");
		// mysql.setC3p0Url("jdbc:mysql://localhost:3306/eos");
		// mysql.setC3p0Username("root");
		// mysql.setC3p0Password("root");
		// mysql.setC3p0Maxpoolsize("2");
		// mysql.setC3p0Minpoolsize("1");
		// mysql.setC3p0Poolsize("2");
		// DataSourceCache.putDataSource(DataSourceCache.DEFAULT_DATASOURCE_NAME,
		// DataSourceFactory.getDataSource(mysql));

		C3p0DataSourceItem oracle = new C3p0DataSourceItem();
		oracle.setC3p0DriverClass("oracle.jdbc.OracleDriver");
		oracle.setC3p0Url("jdbc:oracle:thin:@192.168.4.26:1521:EOS");
		oracle.setC3p0Username("wangwb");
		oracle.setC3p0Password("wangwb");
		oracle.setC3p0Maxpoolsize("2");
		oracle.setC3p0Minpoolsize("1");
		oracle.setC3p0Poolsize("2");
		DataSourceCache.putDataSource(DataSourceCache.DEFAULT_DATASOURCE_NAME, DataSourceFactory.getDataSource(oracle));

	}

	private static void initTransactionManager() {
		ITransactionManagerProvider provider = new DataSourceTransactionManagerSetProvider();
		TxManagerConfigGroup group = new TxManagerConfigGroup();
		group.setPropagation("PROPAGATION_REQUIRED");
		group.setIsolation("ISOLATION_DEFAULT");
		provider.configure(group);
		provider.afterPropertiesSet();
		ITransactionManager manager = provider.getTransactionManager();
		TransactionManagerCacher.put(TransactionManagerCacher.DEFAULT_TX_MANAGER_NAME, manager);
	}

	private static void initDAS() {
		Configuration config = Configuration.initConfiguration(ApplicationContext.getInstance().getApplicationSysConfigurationFile());
		Module[] modules = new Module[] { config.getModule("Das") };
		DASConfigHandler handler = new DASConfigHandler(new Configuration[] { config });
		DASConfigModel model = handler.toModel(modules);
		handler.toLoad(model);
	}
}
