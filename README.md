# All-In-One Tester APK

Egy professzionális Android diagnosztikai alkalmazás Samsung A35 (One UI 8.5, Android 16) optimalizációval.

## 🛠️ Funkciók (21+ teszt)

- 🌐 Internet elérés ellenőrzés
- 🔍 DNS lookup
- 🌍 Publikus IP cím
- ⏱️ Weboldal válaszidő
- ⚡ Sebességteszt
- 📡 Ping
- 🔓 Port scanner
- 📁 Mappa méretezés (Teljes rekurzív méretvizsgálat, Android 11+ MANAGE_EXTERNAL_STORAGE támogatással)
- 🔋 Akku állapot
- 📶 Hálózati állapot
- 💾 RAM használat
- 💿 Tárhely információ
- 📱 Eszköz info
- ☀️ Képernyő fényerő
- 🧠 CPU magok
- 📦 Telepített alkalmazások
- 📱 Mobil hálózat típusa
- 🎯 Szenzorok
- 🖥️ Kijelző felbontás
- 🔒 Biztonsági patch
- ⚠️ Root ellenőrzés

📁 Projektstruktúra (Clean Architecture)

· config/ - Konfigurációk
· domain/usercases/ - Üzleti logika (21 use case)
· presentation/ui/ - UI komponensek (Jetpack Compose)
· utils/ - Segédfüggvények
· data/ - Adatmodellek

🔧 Technológia

· Kotlin 2.0.0 (Legújabb Compose Compilerrel)
· Android Gradle Plugin 8.5.0
· Version Catalogs (libs.versions.toml)
· Jetpack Compose (UI)
· Coroutines (async)
· OkHttp (hálózat)
· Dagger Hilt (DI)

📱 Kompatibilitás

· Minimum: Android 8.0 (API 26)
· Target: Android 16 (API 36)
· Optimalizálva: Samsung Galaxy A35 (One UI 8.5)
