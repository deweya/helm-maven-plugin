# helm-maven-plugin
A Helm plugin for installing a chart from a specified Helm repository

## Why use this plugin?
There are many different Maven plugins that integrate with Helm. However, these plugins are all used to _generate_ Helm charts. This plugin provides a different approach by allowing Maven users to install an already existing Helm chart from a Helm chart repository, OCI registry, or local directory.

You might want to use this plugin in an enterprise setting, where DevOps teams often provide Helm charts for developers throughout the enterprise. These Helm charts, when properly maintained, include best practices and enterprise integrations you won't get from a generic Helm chart generator. By using this plugin, you can leverage trusted and maintained Helm charts throughout your development process.

In order to deploy an application with Helm, you need to build a container image. Note that this plugin does _not_ handle builds, since there are already great plugins that can do this (see [JKube](https://www.eclipse.org/jkube/docs), for example). However, once your image is built, you can use this plugin (helm-maven-plugin) to deploy your container image using the Helm chart that you specify.

## Prerequisites
You must have Helm installed on your machine in order to use this plugin. For instructions on installing Helm, please see Helm's [installation instructions](https://github.com/helm/helm#install).

## What's included? (maven goals)
This plugin comes with two different maven goals:
* helm:upgrade - Install/Upgrade a Helm chart to Kubernetes
* helm:uninstall - Uninstall an existing Helm release

The `helm:upgrade` goal is automatically invoked during the `install` lifecycle phase (as in, when `mvn install` is invoked). See [examples](./examples) for examples on configuring this goal.

The `helm:uninstall` goal is not bound to a lifecycle phase, so it is executed by running the command `mvn helm:uninstall` from the command-line.

Let's explore the `helm:upgrade` goal.

### The `helm:upgrade` goal
This goal is used to install a Helm chart to Kubernetes (it is called `upgrade` to imply the backing command is `helm upgrade --install`). The first time this goal is invoked, it creates a new Helm release. Subsequent invocations create new revisions via a Helm upgrade.

This goal requires POM configuration. See [examples](./examples) for examples on providing this configuration.

The full set of supported parameters are described in the table below:

| Name | Description | Required? | Default |
| ---- | ----------- | --------- | --------- |
| `releaseName` | The Helm release name | No | Defaults to the `project.name` POM setting |
| `namespace` | The Kubernetes namespace to install the Helm chart | No | Defaults to the namespace defined in your kubeconfig context (this is probably `default`, unless your kubeconfig is managed by your distribution, like OpenShift) |
| `wait` | Wait until all resources are created and all pods become ready | No | `false` |
| `chart.name` | The Helm chart name to install | Yes, if installing from an HTTP repository or OCI registry. No, if installing a Helm chart from the local filesystem | null |
| `chart.version` | The Helm chart version to install | No | If not provided, the latest chart version from your repository or registry will be installed |
| `chart.repository.name` | The name of a chart repository previously added with `helm repo add`. This option is mutually exclusive with `chart.repository.url`. | No, _unless_ you want to reference a Helm chart repository previously added with `helm repo add` | null |
| `chart.repository.url` | The repository URL. You can pass a remote repository (such as `https://example.com`), an OCI registry (such as `oci://example.com`), or a local filepath (such as `./example-chart`). This option is mutually exclusive with `chart.repository.name`. | **Yes**, _unless_ you are installing a Helm chart from a repository added with `helm repo add`. | null |
| `chart.repository.username` | The username for authenticating to an HTTP repository via basic auth. Alternatively, you can provide the `HELM_MAVEN_PLUGIN_USERNAME` environment variable | No | null |
| `chart.repository.password` | The password for authenticating to an HTTP repository via basic auth. Alternatively, you can provide the `HELM_MAVEN_PLUGIN_PASSWORD` environment variable | No | null |
| `values.files` | A list of values files to apply (similar to using Helm's `--values` flag | No | null |
| `values.set` | A map of values to set inline (similar to using Helm's `--set` flag | No | null |

#### Providing Helm values
Helm values can be provided with values files or inline configuration. These features are not mutually exclusive and can be used together.

Below shows how to provide multiple values files in the plugin's configuration:
```xml
<values>
  <files>
    <file>example-values.yaml</file>
    <file>another-values-file.yaml</file>
  </files>
</values>
```

Below shows how to provide inline values in the plugin's configuration:
```xml
<values>
  <set>
    <replicaCount>1</replicaCount>
    <service.type>ClusterIP</service.type>
  </set>
</values>
```

Providing values can be explored further in [this example](./examples/http-repository).

#### Authentication
Currently, only basic auth for HTTP repositories is supported using the `chart.repository.username` and `chart.repository.password` settings, or the `HELM_MAVEN_PLUGIN_USERNAME` and `HELM_MAVEN_PLUGIN_PASSWORD` environment variables.

Authentication can be explored further in [this example](./examples/http-repository).

### The `helm:uninstall` goal
This goal is used to uninstall (delete) a Helm release from your Kubernetes namespace. It is not bound to a Maven lifecycle, so you can invoke it directly from the command line by running:
```
mvn helm:uninstall
```

This goal shares some of the same configuration as `helm:upgrade`. Their descriptions remain the same as they were described in the previous section:
* `releaseName`
* `namespace`
* `wait`

See [examples](./examples) for examples on providing this configuration.
