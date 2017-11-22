package ahmed.com.mvp_rx_viewmodel.rx;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RunOn
{
    SchedulerType value() default SchedulerType.IO;
}
