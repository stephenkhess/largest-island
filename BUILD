load("@io_bazel_rules_groovy//groovy:groovy.bzl", "groovy_and_java_library", "spock_test")

groovy_and_java_library(
    name = "prodlib",
    srcs = glob(["src/main/groovy/**/*.groovy", "src/main/java/**/*.java"]),
)

spock_test(
    name = "tests",
    specs = glob(["src/test/groovy/**/*Spec.groovy"]),
    deps = [":prodlib"],
)