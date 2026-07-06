package core.listeners;

import core.annotations.RunForRegulations;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class RegulationMethodInterceptor implements IMethodInterceptor {

    private static final String REGULATION_PARAMETER = "regulation";

    @Override
    public List<IMethodInstance> intercept(
            List<IMethodInstance> methods,
            ITestContext context
    ) {
        String regulation = context
                .getCurrentXmlTest()
                .getParameter(REGULATION_PARAMETER);

        if (regulation == null || regulation.isBlank()) {
            throw new IllegalStateException(
                    "TestNG parameter 'regulation' is missing for test: "
                            + context.getCurrentXmlTest().getName()
            );
        }

        String currentRegulation = normalize(regulation);

        return methods.stream()
                .filter(method -> shouldRun(method, currentRegulation))
                .collect(Collectors.toList());
    }

    private boolean shouldRun(
            IMethodInstance methodInstance,
            String currentRegulation
    ) {
        Method javaMethod = methodInstance
                .getMethod()
                .getConstructorOrMethod()
                .getMethod();

        if (javaMethod == null) {
            return true;
        }

        RunForRegulations annotation =
                javaMethod.getAnnotation(RunForRegulations.class);

        /*
         * Metoda bez anotacije pokreće se za sve regulative.
         */
        if (annotation == null) {
            return true;
        }

        /*
         * Metoda sa anotacijom pokreće se samo ako se trenutna
         * regulativa nalazi u anotaciji.
         */
        return Arrays.stream(annotation.value())
                .map(RegulationMethodInterceptor::normalize)
                .anyMatch(currentRegulation::equals);
    }

    private static String normalize(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }
}