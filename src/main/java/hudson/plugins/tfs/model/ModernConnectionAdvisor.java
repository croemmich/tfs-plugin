package hudson.plugins.tfs.model;

import java.util.Locale;
import java.util.TimeZone;

import com.microsoft.tfs.core.config.ConnectionInstanceData;
import com.microsoft.tfs.core.config.DefaultConnectionAdvisor;
import com.microsoft.tfs.core.config.httpclient.HTTPClientFactory;
import com.microsoft.tfs.core.config.persistence.DefaultPersistenceStoreProvider;
import com.microsoft.tfs.core.config.persistence.PersistenceStoreProvider;

public class ModernConnectionAdvisor extends DefaultConnectionAdvisor {

    private final ProxyHostEx proxyHost;
    private final PersistenceStoreProvider persistenceStoreProvider;

    public ModernConnectionAdvisor(final ProxyHostEx proxyHost) {
        this(proxyHost, null);
    }

    public ModernConnectionAdvisor(final ProxyHostEx proxyHost, final PersistenceStoreProvider persistenceStoreProvider) {
        super(Locale.getDefault(), TimeZone.getDefault());
        this.proxyHost = proxyHost;
        this.persistenceStoreProvider = persistenceStoreProvider;
    }

    @Override
    public PersistenceStoreProvider getPersistenceStoreProvider(final ConnectionInstanceData instanceData) {
        return persistenceStoreProvider != null
                ? persistenceStoreProvider
                : DefaultPersistenceStoreProvider.INSTANCE;
    }

    @Override
    public HTTPClientFactory getHTTPClientFactory(final ConnectionInstanceData connectionInstanceData) {
        return new ModernHTTPClientFactory(connectionInstanceData, proxyHost);
    }
}
