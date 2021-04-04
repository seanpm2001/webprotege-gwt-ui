package edu.stanford.bmir.protege.web.server.inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Ticker;
import dagger.Module;
import dagger.Provides;
import edu.stanford.bmir.protege.web.server.app.ApplicationDisposablesManager;
import edu.stanford.bmir.protege.web.server.app.ApplicationSettingsManager;
import edu.stanford.bmir.protege.web.server.app.WebProtegeProperties;
import edu.stanford.bmir.protege.web.server.dispatch.DispatchServiceExecutor;
import edu.stanford.bmir.protege.web.server.dispatch.impl.DispatchServiceExecutorImpl;
import edu.stanford.bmir.protege.web.server.jackson.ObjectMapperProvider;
import edu.stanford.bmir.protege.web.server.mail.*;
import edu.stanford.bmir.protege.web.server.owlapi.NonCachingDataFactory;
import edu.stanford.bmir.protege.web.server.upload.*;
import edu.stanford.bmir.protege.web.server.util.DisposableObjectManager;
import edu.stanford.bmir.protege.web.shared.app.ApplicationSettings;
import edu.stanford.bmir.protege.web.shared.inject.ApplicationSingleton;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntityProvider;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 17/02/16
 */
@Module
public class ApplicationModule {

    private static final int MAX_FILE_DOWNLOAD_THREADS = 5;

    private static final int INDEX_UPDATING_THREADS = 10;


    @ApplicationSingleton
    @Provides
    public ObjectMapper provideObjectMapper(ObjectMapperProvider provider) {
        return provider.get();
    }

    @Provides
    public DispatchServiceExecutor provideDispatchServiceExecutor(DispatchServiceExecutorImpl impl) {
        return impl;
    }

    @Provides
    @ApplicationSingleton
    @ApplicationDataFactory
    public OWLDataFactory provideOWLDataFactory() {
        return new NonCachingDataFactory(new OWLDataFactoryImpl());
    }

    @Provides
    @ApplicationDataFactory
    @ApplicationSingleton
    public OWLEntityProvider provideOWLProvider(@ApplicationDataFactory OWLDataFactory dataFactory) {
        return dataFactory;
    }

    @Provides
    @ApplicationSingleton
    public WebProtegeProperties provideWebProtegeProperties(WebProtegePropertiesProvider povider) {
        return povider.get();
    }

    @Provides
    public SendMail provideSendMail(SendMailImpl manager) {
        return manager;
    }

    @Provides
    public MessagingExceptionHandler provideMessagingExceptionHandler(MessagingExceptionHandlerImpl handler) {
        return handler;
    }

    @Provides
    public ApplicationSettings provideApplicationSettings(ApplicationSettingsManager manager) {
        return manager.getApplicationSettings();
    }

    @ApplicationSingleton
    @Provides
    ApplicationDisposablesManager provideApplicationDisposableObjectManager(DisposableObjectManager disposableObjectManager) {
        return new ApplicationDisposablesManager(disposableObjectManager);
    }

    @Provides
    Ticker provideTicker() {
        return Ticker.systemTicker();
    }

    @Provides
    DocumentResolver provideDocumentResolver(DocumentResolverImpl impl) {
        return impl;
    }

}
