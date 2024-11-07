# Security Policy for Quran API SDK

## Overview

We take security seriously at Quran API SDK. We recognize the importance of protecting our users and contributors by proactively identifying and resolving security vulnerabilities in our software. This document outlines the security support and reporting guidelines for the Quran API SDK.

## Supported Versions

Our commitment to security updates and patches applies to the following versions:

| Version   | Supported          |
| --------- | ------------------ |
| 0.9.x     | ✅ Fully supported |
| < 0.9     | ❌ Not supported   |

Older versions may not receive security updates. If you are using an unsupported version, we encourage upgrading to the latest release.

## Reporting a Vulnerability

If you discover a security vulnerability in the Quran API SDK, please report it responsibly. We will address all reported issues swiftly to protect the integrity of our software. Please follow the guidelines below to report vulnerabilities:

1. **Contact**: Send a detailed report to [security@quranapi.com](mailto:security@quranapi.com). We prefer reports in English but will accommodate other languages if possible.
2. **Include the Following**:
   - A clear description of the vulnerability and its potential impact.
   - Steps to reproduce the vulnerability or a proof-of-concept code snippet.
   - The version or commit hash of the SDK where the vulnerability was found.
   - Any potential patches, if available, are greatly appreciated.
3. **Initial Response Time**: We aim to respond within 72 hours, acknowledging the receipt of your report.

## Responsible Disclosure Policy

We follow a responsible disclosure process to ensure the safety and security of our users:

- **Private Communication**: We request that you report vulnerabilities privately to us first. Please avoid public disclosure of the vulnerability until we have resolved it.
- **Timeline for Resolution**: We aim to validate and resolve confirmed vulnerabilities within 14 days, although complex issues may require additional time.
- **Public Credit**: We are happy to recognize and credit individuals who responsibly disclose valid security issues. If you would like to be acknowledged, please let us know.

### Steps After Reporting
1. **Investigation**: Our team will validate and investigate the vulnerability.
2. **Fix Development**: Once confirmed, we will work on a fix, which may include patches or security updates.
3. **Release Update**: After testing the fix, we will release it as part of a minor or patch version update.
4. **Public Notification**: If necessary, we may notify users of significant security updates.

## Best Practices for Reporting Security Issues

To help us address the issue efficiently, please adhere to these best practices when reporting a vulnerability:
- **Avoid Exploits**: Please do not test vulnerabilities in production environments.
- **Test on Latest Version**: If possible, confirm the issue on the latest version of the SDK.
- **Respect User Privacy**: If your vulnerability involves data exposure, please take care to avoid exposing user data during testing.

## Security Best Practices for Developers Using Quran API SDK

To help our users maintain secure implementations, we encourage the following best practices:
- **Keep the SDK Updated**: Always use the latest version of the SDK to benefit from security patches.
- **Minimize Permissions**: Only request permissions your app needs.
- **Encrypt Sensitive Data**: When handling sensitive user data, always use encryption.
- **Limit Exposure**: Avoid unnecessary exposure of SDK components to prevent unauthorized access.

## Contact Information

If you have any questions about security practices or reporting vulnerabilities, please feel free to reach out to us at [sourav.0.alim@gmail.com](mailto:sourav.0.alim@gmail.com).

## Thank You

We appreciate the efforts of our users and security researchers in helping us ensure the safety of the Quran API SDK. Your contributions help us maintain a secure and reliable experience for everyone. Thank you for working with us responsibly to protect our community.
