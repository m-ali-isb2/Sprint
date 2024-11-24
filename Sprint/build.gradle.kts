import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.formula.sprint"
    compileSdk = 33
/*    def githubProperties = new Properties()
    githubProperties.load(new FileInputStream(rootProject.file("github.properties")))*/

    defaultConfig {
//        applicationId = "com.executors.pulse"
        minSdk = 24
        //        versionCode = 1
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        version = 1
        ndkVersion = "1.0.1"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

/**Create github.properties in root project folder file with gpr.usr=GITHUB_USER_ID  & gpr.key=PERSONAL_ACCESS_TOKEN**/
var githubProperties =  Properties()
githubProperties.load(FileInputStream(rootProject.file("github.properties")))


var getVersionName ="1.0.1"

var getArtificatId = "sprint"


publishing {
    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = "com.formula"
                artifactId = getArtificatId
                version = getVersionName

                // Compatible with a number of Gradle lazy APIs that accept also java.io.File
                val output: Provider<RegularFile> = layout.buildDirectory.file("/outputs/aar/${getArtificatId}-release.aar")

// If you really need the java.io.File for a non lazy API
                output.get().asFile

// Or a path for a lazy String based API
                output.map {
                    artifact(it.asFile.path)
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            /** Configure path of your package repository on Github
             *  Replace GITHUB_USERID with your/organisation Github userID and REPOSITORY with the repository name on GitHub
             */
            url = uri("https://maven.pkg.github.com/${githubProperties.get("gpr.usr")}/Sprint") // Github Package
            credentials {
                //Fetch these details from the properties file or from Environment variables
                username = githubProperties.get("gpr.usr") as String? ?: System.getenv("GPR_USER")
                password = githubProperties.get("gpr.key") as String? ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}
/*publishing {
    publications {
        mavenJava(MavenPublication) {
            id 'com.enefce.libraries'
            artifactId getArtificatId
            version getVersionName
            artifact("$buildDir/outputs/aar/${getArtificatId()}-release.aar")
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            *//** Configure path of your package repository on Github
             *  Replace GITHUB_USERID with your/organisation Github userID and REPOSITORY with the repository name on GitHub
             *//*
            url = uri("https://maven.pkg.github.com/GITHUB_USERID/REPOSITORY")

            credentials {
                *//**Create github.properties in root project folder file with gpr.usr=GITHUB_USER_ID  & gpr.key=PERSONAL_ACCESS_TOKEN**//*
                username = githubProperties['gpr.usr'] ?: System.getenv("GPR_USER")
                password = githubProperties['gpr.key'] ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}*/


dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}