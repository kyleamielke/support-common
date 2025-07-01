package io.thatworked.support.common.test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import io.thatworked.support.common.entity.base.BaseEntity;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Date;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * Reusable architecture test rules for enforcing standards across services.
 * Import and use these in your service's architecture tests.
 */
public class ArchitectureRules {
    
    /**
     * Ensures all JPA entities extend BaseEntity or AuditableEntity.
     */
    public static ArchRule entitiesShouldExtendBaseEntity() {
        return classes()
            .that().areAnnotatedWith("javax.persistence.Entity")
            .or().areAnnotatedWith("jakarta.persistence.Entity")
            .should().beAssignableTo(BaseEntity.class)
            .because("All entities must extend BaseEntity to ensure consistent field naming");
    }
    
    /**
     * Prevents use of LocalDateTime in favor of Instant.
     */
    public static ArchRule noLocalDateTimeAllowed() {
        return noClasses()
            .should().dependOnClassesThat().areAssignableFrom(LocalDateTime.class)
            .orShould().dependOnClassesThat().areAssignableFrom(LocalDate.class)
            .because("Use Instant for all timestamps to ensure consistent timezone handling");
    }
    
    /**
     * Prevents use of java.util.Date in favor of java.time classes.
     */
    public static ArchRule noJavaUtilDateAllowed() {
        return noClasses()
            .should().dependOnClassesThat().areAssignableFrom(Date.class)
            .because("Use java.time classes (Instant) instead of java.util.Date");
    }
    
    /**
     * Ensures domain layer doesn't depend on infrastructure or framework classes.
     */
    public static ArchRule domainShouldNotDependOnFrameworks() {
        return noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                "org.springframework..",
                "jakarta.persistence..",
                "javax.persistence..",
                "org.hibernate.."
            )
            .because("Domain layer must be framework-independent");
    }
    
    /**
     * Ensures proper layered architecture.
     */
    public static ArchRule layeredArchitectureShouldBeRespected() {
        return layeredArchitecture()
            .consideringAllDependencies()
            .layer("API").definedBy("..api..")
            .layer("Application").definedBy("..application..")
            .layer("Domain").definedBy("..domain..")
            .layer("Infrastructure").definedBy("..infrastructure..")
            
            .whereLayer("API").mayNotBeAccessedByAnyLayer()
            .whereLayer("Application").mayOnlyBeAccessedByLayers("API")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure", "API")
            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer();
    }
    
    /**
     * Ensures boolean fields follow naming convention.
     */
    public static ArchRule booleanFieldsShouldStartWithIs() {
        return fields()
            .that().haveRawType(boolean.class)
            .or().haveRawType(Boolean.class)
            .should().haveNameMatching("^is[A-Z].*")
            .because("Boolean fields should start with 'is' for clarity");
    }
    
    /**
     * Ensures DTOs don't contain business logic.
     */
    public static ArchRule dtosShouldNotContainBusinessLogic() {
        return classes()
            .that().resideInAPackage("..dto..")
            .should().onlyHaveDependentClassesThat()
            .resideInAnyPackage("..dto..", "..api..", "..mapper..")
            .because("DTOs should only be data containers without business logic");
    }
    
    /**
     * Run all standard rules against the given classes.
     */
    public static void checkAllRules(JavaClasses classes) {
        entitiesShouldExtendBaseEntity().check(classes);
        noLocalDateTimeAllowed().check(classes);
        noJavaUtilDateAllowed().check(classes);
        domainShouldNotDependOnFrameworks().check(classes);
        layeredArchitectureShouldBeRespected().check(classes);
        booleanFieldsShouldStartWithIs().check(classes);
        dtosShouldNotContainBusinessLogic().check(classes);
    }
}