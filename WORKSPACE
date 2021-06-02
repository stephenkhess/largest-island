load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Official Bazel tools to get Maven repo dependencies; there are other
# unofficial tools also.

RULES_JVM_EXTERNAL_TAG = "3.0"

RULES_JVM_EXTERNAL_SHA = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

http_archive(
    name = "io_bazel_rules_groovy",
    url = "https://github.com/bazelbuild/rules_groovy/archive/0.0.6.tar.gz",
    sha256 = "21c7172786623f280402d3b3a2fc92f36568afad5a4f6f5ea38fd1c6897aecf8",
    strip_prefix = "rules_groovy-0.0.6",
)

load("@io_bazel_rules_groovy//groovy:repositories.bzl", "rules_groovy_dependencies")
rules_groovy_dependencies()

maven_install(
    artifacts = [
        "org.codehaus.groovy:groovy-all:2.3.10",
        "org.spockframework:spock-core:1.0-groovy-2.3"
    ],
    repositories = [
        "https://jcenter.bintray.com/",
        "https://repo1.maven.org/maven2",
    ],
)
