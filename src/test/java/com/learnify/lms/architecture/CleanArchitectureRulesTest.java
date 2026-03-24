package com.learnify.lms.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.learnify.lms", importOptions = ImportOption.DoNotIncludeTests.class)
class CleanArchitectureRulesTest {

  @ArchTest
  static final ArchRule domain_must_not_depend_on_outer_layers =
      noClasses()
          .that()
          .resideInAPackage("..domain..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("..application..", "..presentation..", "..infrastructure..");

  @ArchTest
  static final ArchRule application_must_not_depend_on_presentation =
      noClasses()
          .that()
          .resideInAPackage("..application..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("..presentation..");

  @ArchTest
  static final ArchRule presentation_must_not_depend_on_infrastructure =
      noClasses()
          .that()
          .resideInAPackage("..presentation..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("..infrastructure..");

  @ArchTest
  static final ArchRule application_must_not_depend_on_vendor_frameworks =
      noClasses()
          .that()
          .resideInAPackage("..application..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(
              "com.cloudinary..",
              "io.jsonwebtoken..",
              "org.springframework.data..",
              "org.hibernate..",
              "software.amazon.awssdk..");

  @ArchTest
  static final ArchRule presentation_controllers_must_end_with_controller =
      classes()
          .that()
          .resideInAPackage("..presentation.controller..")
          .should()
          .haveSimpleNameEndingWith("Controller");

  @ArchTest
  static final ArchRule infrastructure_configs_must_end_with_config =
      classes()
          .that()
          .resideInAPackage("..infrastructure.config..")
          .should()
          .haveSimpleNameEndingWith("Config");

  @ArchTest
  static final ArchRule domain_repository_interfaces_must_end_with_repository =
      classes()
          .that()
          .areInterfaces()
          .and()
          .resideInAPackage("..domain.repository..")
          .should()
          .haveSimpleNameEndingWith("Repository");

  @ArchTest
  static final ArchRule application_services_should_be_named_service_or_impl =
      classes()
          .that()
          .resideInAPackage("..application.service..")
          .should()
          .haveNameMatching(".*\\.(\\w+Service|\\w+ServiceImpl)");

  @ArchTest
  static final ArchRule jpa_adapters_should_follow_jpa_repository_naming =
      classes()
          .that()
          .resideInAPackage("..infrastructure.persistence.jpa..")
          .should()
          .haveNameMatching(".*\\.Jpa\\w+Repository");

  @ArchTest
  static final ArchRule application_port_interfaces_should_end_with_service =
      classes()
          .that()
          .areInterfaces()
          .and()
          .resideInAPackage("..application.port..")
          .should()
          .haveSimpleNameEndingWith("Service");
}
