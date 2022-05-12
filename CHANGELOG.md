# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2022/05/12
### Added
- Added basic auth capability for HTTP repositories. Users can provide the `chart.repository.username` and `chart.repository.password` configs, or they can set the `HELM_MAVEN_PLUGIN_USERNAME` and `HELM_MAVEN_PLUGIN_PASSWORD` environment variables

## [1.0.0] - 2022/05/09
### Added
- Initial release