import com.android.build.gradle.internal.scope.publishBuildArtifacts
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.arjesh.permissionmanager"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

//    task("sourceJar") {
//        sourceSets["main"].java.getSourceFiles()
//        archivesName = "sources"
//    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

//    configure<PublishingExtension> {
//        publications.create<MavenPublication>("permissionManager") {
//            components.getByName("release")
//            groupId = "io.github.arjesh"
//            artifactId = "permissionManager"
//            version = "1.0.4-alpha.2"
//        }
//        repositories {
//            mavenLocal()
//        }
//    }


}

project.afterEvaluate {
    configure<PublishingExtension> {
        publications.create<MavenPublication>("permissionManager") {
            components.getByName("release")
            groupId = "io.github.arjesh"
            artifactId = "permissionManager"
            version = "1.0.4-alpha.3"
        }
        repositories {
            mavenLocal()
        }
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
}