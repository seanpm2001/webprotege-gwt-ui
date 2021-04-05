package edu.stanford.bmir.protege.web.shared.project;

import com.google.auto.value.AutoValue;
import com.google.common.annotations.GwtCompatible;
import edu.stanford.bmir.protege.web.shared.HasUserId;
import edu.stanford.bmir.protege.web.shared.dispatch.Result;
import edu.stanford.bmir.protege.web.shared.user.UserId;

import javax.annotation.Nonnull;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 05/04/2013
 */
@AutoValue
@GwtCompatible(serializable = true)
public abstract class LoadProjectResult implements Result, HasUserId, HasProjectId {

    public static LoadProjectResult get(@Nonnull ProjectId projectId,
                                        @Nonnull UserId loadedBy,
                                        @Nonnull ProjectDetails projectDetails) {
        return new AutoValue_LoadProjectResult(projectId,
                                               loadedBy,
                                               projectDetails);
    }

    @Nonnull
    public abstract ProjectId getProjectId();

    @Override
    public abstract UserId getUserId();

    @Nonnull
    public abstract ProjectDetails getProjectDetails();
}
