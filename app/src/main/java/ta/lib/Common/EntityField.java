package ta.lib.Common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface EntityField
{
}
