# Proposal

## Background

The pitfalls of using centralized social media platforms for individuals and institutions have been well documents and analyzed. Those pitfalls led to the rise of decentralized social media platform. Initially, the technical challenges surrounding the developments and maintenance were high. That however has changed significantly with open-source solutions, widely accepted open protocols and the lowering cost of cloud services for infrastructure.   

Centralized social media as part of Web 2.0 was envisioned as a way to digitize social interaction among individuals and groups. While it did originally show promise, there were unintended consequences. Some of the well-known downsides are user tracking, negative mental health impact on individuals, and proliferation of extreme content. 

More than 90% of users on these platforms are passive consumers. Since these platforms serve content to user algorithmically, they have turned into content consumption systems rather than meaningful social engagements. 
Combating misinformation and managing bad actors in these spaces have been an ever-growing problem. A large part of the consequences stem with the large user bases of these platforms. There are platforms with billions of users. User data monetization of a free service led to widespread tracking and privacy concerns.Social media platforms with billions of users essentially turned the business model of a digital townhouse to advertising engines of the current internet. 

These problems have been highlighted many times and platforms have taken steps to rectify them. However, they have largely been ineffective or controversial. To solve the many problems of large centrally governed platforms, decentralized social media platforms led the charge. Services like Mastodon have demonstrably proven that with open decentralized protocols, and a smaller community based approach is a better solution for lots of social applications.

## Introduction

> [!IMPORTANT]
> The basic project objectives are: Implementation of a simple client-server system that is capable of federation via the ActivityPub Protocol.

### Hardware Requirements
Since we are targeting a cloud based system, there are no upfront hardware requirements for this system.

### Software Requirements
In terms of software, we are attempting to leverage a Typescript based hybrid front-end using Ionic framework alongside Angular, and Java Spring Boot for the backend. 

Additionally we're going to employ a noSQL database, specifically Postgres, to manage persistence.

## Prospective Users

The current scope would be limited to known niche communities, universities, neighbourhoods, and such like. 

## Alternatives
There are already plenty of client and server implementation of the ActivityPub protocol. That being said, we want to explore the possibility of creating a system where different features could be plugged-in depending on the server admin's requirements. plugins could include features such as content-filters, language support, themes etc.





