# DEVELOPMENT.md

# SmartFinance Development Guide

Dokumen ini menjadi acuan implementasi seluruh project SmartFinance.

---

# Architecture

Project menggunakan:

- Clean Architecture
- MVVM
- Repository Pattern
- Single Activity
- Jetpack Compose

Flow

UI

↓

ViewModel

↓

UseCase

↓

Repository

↓

Local / Remote Data Source

↓

Room / Firebase / AI

---

# Folder Structure

src/

components/

data/

domain/

presentation/

navigation/

service/

di/

utils/

ui.theme/

MainActivity.kt

---

# Folder Responsibilities

## components

Berisi reusable composable.

Contoh

- AppCard
- PrimaryButton
- AppTextField
- BudgetProgress
- TransactionItem

Tidak boleh memiliki business logic.

---

## presentation

Berisi seluruh Screen dan ViewModel.

Setiap Screen memiliki ViewModel sendiri.

Contoh

DashboardScreen

↓

DashboardViewModel

↓

DashboardUiState

---

## domain

Berisi:

Repository Interface

UseCase

Business Logic

Tidak boleh bergantung pada Android Framework.

---

## data

Berisi

Entity

DAO

Repository Implementation

Mapper

Data Source

---

## service

Berisi service Android.

Contoh

NotificationListenerService

AI Service

Backup Service

Worker

---

## navigation

Seluruh route aplikasi.

Tidak boleh ada hardcoded navigation pada Screen.

---

## utils

Formatter

Extension

Helper

Validator

---

# Naming Convention

Screen

DashboardScreen

ViewModel

DashboardViewModel

UiState

DashboardUiState

Repository

DashboardRepository

UseCase

GetTransactionUseCase

Dao

TransactionDao

Entity

TransactionEntity

---

# UI State

Gunakan sealed interface atau data class.

Minimal memiliki

Loading

Success

Empty

Error

Jangan menggunakan Boolean Loading.

---

# Repository Pattern

Repository menjadi satu-satunya akses data.

UI

↓

ViewModel

↓

Repository

↓

Room

atau

Firebase

atau

AI

---

# Database

Room

Entity

- User
- Transaction
- Budget
- Category

Gunakan Flow untuk observe data.

Migration wajib dibuat jika schema berubah.

---

# Dependency Injection

Gunakan Hilt.

Pisahkan module

- DatabaseModule
- RepositoryModule
- ServiceModule
- NetworkModule

Jangan membuat object singleton manual.

---

# Navigation

Start Destination

Splash

Flow

Splash

↓

Onboarding

↓

Login

↓

Dashboard

↓

Transaction

↓

Profile

↓

Settings

---

# Notification Reader

Menggunakan

NotificationListenerService

Flow

Notification

↓

Parser

↓

DraftTransaction

↓

User Confirmation

↓

Save

Parser dipisahkan dari Service.

---

# Gmail Integration

Flow

Gmail API

↓

Email Parser

↓

Transaction Parser

↓

Draft Transaction

↓

Confirmation

↓

Save

---

# AI Service

AI hanya bertugas

- Auto Category
- Merchant Detection
- Insight
- Recommendation

AI tidak boleh langsung menyimpan data.

---

# Error Handling

Gunakan Result atau sealed class.

Jangan melempar Exception langsung ke UI.

Seluruh Error harus memiliki pesan yang jelas.

---

# Performance

Gunakan

LazyColumn

remember

derivedStateOf

StateFlow

Hindari

Nested LazyColumn

Query Database di UI

Recomposition berlebihan

---

# Compose Best Practices

State Hoisting

Reusable Component

Immutable State

Material3

Preview untuk Component

Modifier sebagai parameter pertama

Jangan membuat Composable terlalu besar (>300 baris). Pecah menjadi komponen yang lebih kecil.

---

# Testing

Minimal

Repository Test

ViewModel Test

UseCase Test

UI Test untuk flow utama

---

# Logging

Debug

Logcat

Release

Crashlytics

Jangan menyimpan data sensitif pada log.

---

# Security

Jangan simpan

Password

PIN

OTP

CVV

Nomor Kartu

Gunakan Encrypted DataStore apabila menyimpan data sensitif.

---

# Git Convention

Branch

feature/

fix/

refactor/

docs/

Commit

feat:

fix:

refactor:

docs:

style:

chore:

---

# Code Review Checklist

Sebelum merge pastikan

- Mengikuti AGENTS.md
- UI sesuai DESIGN.md
- Tidak ada hardcode
- Tidak ada duplicate code
- Menggunakan reusable component
- Mendukung Dark Mode
- Tidak ada warning
- Tidak crash

---

# Future Structure

domain/

usecase/

repository/

model/

data/

local/

remote/

mapper/

presentation/

screen/

viewmodel/

state/

event/

service/

notification/

gmail/

ai/

worker/

---

# Project Roadmap

Phase 1

- Login
- Dashboard
- Transaction

Phase 2

- Budget
- Analytics
- Category

Phase 3

- Notification Reader
- Gmail Integration
- AI

Phase 4

- Backup
- Theme
- Security

Phase 5

- Export PDF


---

# Final Principles

- Kode harus sederhana dan mudah dipelihara.
- Hindari over-engineering.
- Semua UI mengikuti DESIGN.md.
- Semua implementasi mengikuti AGENTS.md.
- Semua fitur mengikuti FEATURES.md.
- Fokus pada pengalaman pengguna, performa, dan keamanan.
