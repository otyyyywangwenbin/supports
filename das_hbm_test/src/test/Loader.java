package test;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.primeton.ext.das.entity.startup.DBQueryFileLoader;
import com.primeton.ext.das.entity.startup.HBMFileLoader;
import com.primeton.ext.das.sql.startup.NamedSqlFileLoader;
import com.primeton.ext.data.sdo.startup.XSDModelLoader;
import com.primeton.ext.runtime.resource.model.IModelResource;
import com.primeton.ext.runtime.resource.model.IResourceCollection;
import com.primeton.runtime.resource.impl.FileModelResource;
import com.primeton.runtime.resource.impl.model.ResourceCollectionImpl;
import com.primeton.spring.context.ApplicationContextFactory;
import com.primeton.spring.loader.SpringConfigFileCache;

public class Loader {
	@SuppressWarnings("unchecked")
	public static void loadModelFiles(File dir) {
		{
			Collection<File> fileList = FileUtils.listFiles(dir, XSD_FILTER, TrueFileFilter.TRUE);
			loadXSD(fileList.toArray(new File[fileList.size()]));
		}
		{
			Collection<File> fileList = FileUtils.listFiles(dir, HBM_FILTER, TrueFileFilter.TRUE);
			loadHBM(fileList.toArray(new File[fileList.size()]));
		}
		{
			Collection<File> fileList = FileUtils.listFiles(dir, DBQUERY_FILTER, TrueFileFilter.TRUE);
			loadDBQuery(fileList.toArray(new File[fileList.size()]));
		}
		{
			Collection<File> fileList = FileUtils.listFiles(dir, NAMEDSQL_FILTER, TrueFileFilter.TRUE);
			loadNamedSql(fileList.toArray(new File[fileList.size()]));
		}
		{
			Collection<File> fileList = FileUtils.listFiles(dir, SPRING_FILTER, TrueFileFilter.TRUE);
			loadSpring(fileList.toArray(new File[fileList.size()]));
		}
	}

	public static void loadXSD(File... files) {
		new XSDModelLoader().load(toResourceCollection(files));
		XSDModelLoader.waitLoadXSD(10 * 1000);
	}

	public static void loadHBM(File... files) {
		new HBMFileLoader().load(toResourceCollection(files));
	}

	public static void loadDBQuery(File... files) {
		new DBQueryFileLoader().load(toResourceCollection(files));
	}

	public static void loadNamedSql(File... files) {
		new NamedSqlFileLoader().load(toResourceCollection(files));

	}

	public static void loadSpring(File... files) {
		for (File file : files) {
			SpringConfigFileCache.INSTANCE.putResource(file.getAbsolutePath());
		}
		ApplicationContextFactory.setNeedReload(true);
	}

	private static IResourceCollection toResourceCollection(File... files) {
		ResourceCollectionImpl collection = new ResourceCollectionImpl();
		IModelResource[] resources = toModelResources(files);
		for (IModelResource resource : resources) {
			collection.addAddedResource(resource);
		}
		return collection;
	}

	private static IModelResource[] toModelResources(File... files) {
		IModelResource[] resources = new IModelResource[files.length];
		for (int i = 0; i < files.length; i++) {
			resources[i] = new FileModelResource(files[i]) {
				private static final long serialVersionUID = 1L;

				public String getURI() {
					return getPath();
				}

			};
		}
		return resources;
	}

	// ////////////////
	public static final IOFileFilter XSD_FILTER = new AbstractFileFilter() {
		public boolean accept(File arg0, String name) {
			return name.endsWith(".xsd");
		}
	};

	public static final IOFileFilter HBM_FILTER = new AbstractFileFilter() {
		public boolean accept(File arg0, String name) {
			return name.endsWith(".hbm");
		}
	};

	public static final IOFileFilter DBQUERY_FILTER = new AbstractFileFilter() {
		public boolean accept(File arg0, String name) {
			return name.endsWith(".dbquery");
		}
	};

	public static final IOFileFilter NAMEDSQL_FILTER = new AbstractFileFilter() {
		public boolean accept(File arg0, String name) {
			return name.endsWith(".namingsql");
		}
	};

	public static final IOFileFilter SPRING_FILTER = new AbstractFileFilter() {
		public boolean accept(File arg0, String name) {
			return name.endsWith("beans.xml");
		}
	};

}
