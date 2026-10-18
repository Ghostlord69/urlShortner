# urlShortner

A full-stack URL shortening platform with secure authentication and click analytics. Create short, shareable links and track how they perform over time.

Inspired by services like Bitly, this project demonstrates production-minded patterns: JWT-based auth, protected APIs, redirect handling, and a responsive analytics dashboard.

---

## Features

- **User authentication** — Register and log in with JWT (Spring Security + BCrypt)
- **URL shortening** — Generate unique 8-character short codes for long URLs
- **Instant redirects** — Resolve short links with HTTP 302 and forward to the original destination
- **Click tracking** — Record each visit and increment per-link click counts
- **Analytics dashboard** — View all of your links, aggregate traffic, and date-wise charts
- **Protected routes** — Frontend private routes and backend method-level authorization
- **Responsive UI** — Landing page, auth screens, and dashboard built with React and Tailwind CSS

---

## Tech Stack

| Layer | Technologies |
| --- | --- |
| **Frontend** | React 18, Vite, Tailwind CSS, Material UI, Axios, React Query, Chart.js, React Router |
| **Backend** | Java 21, Spring Boot 3.4, Spring Security, Spring Data JPA, JJWT |
| **Database** | PostgreSQL 16 |
| **Tooling** | Docker Compose, Maven Wrapper, npm |

---

## Architecture

```
┌─────────────────┐      JWT REST       ┌──────────────────┐
│  React (Vite)   │ ──────────────────► │  Spring Boot API │
│  :5173          │                     │  :8080           │
└─────────────────┘                     └────────┬─────────┘
                                                 │
                                        ┌────────▼─────────┐
                                        │   PostgreSQL     │
                                        │   :5432          │
                                        └──────────────────┘
```

**Data model**

| Entity | Description |
| --- | --- |
| `User` | Account credentials and role |
| `UrlMapping` | Original URL, short code, click count, owner |
| `ClickEvent` | Timestamped click for analytics |

---

## Project Structure

```text
urlShortner/
├── docker-compose.yml          # Local PostgreSQL
├── url-shortener-sb/           # Spring Boot backend
│   ├── src/main/java/...       # Controllers, security, services, models
│   └── src/main/resources/     # application.properties
└── url-shortener-react/        # React frontend
    └── src/                    # Pages, dashboard, auth, API client
```

---

## Getting Started

### Prerequisites

- **JDK 17+** (21 recommended)
- **Node.js 18+**
- **Docker** (Docker Desktop or OrbStack)

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd urlShortner
```

### 2. Start PostgreSQL

```bash
docker compose up -d
```

This starts a PostgreSQL instance on `localhost:5432` with:

| Setting | Value |
| --- | --- |
| Database | `urlshortner` |
| Username | `postgres` |
| Password | `postgres` |

### 3. Run the backend

```bash
cd url-shortener-sb
./mvnw spring-boot:run
```

API available at **http://localhost:8080**

Optional environment overrides (see `url-shortener-sb/.env.example`):

```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/urlshortner
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres
export DATABASE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
export JWT_SECRET=<base64-encoded-secret-at-least-256-bits>
export FRONTEND_URL=http://localhost:5173
./mvnw spring-boot:run
```

> **Note:** `JWT_SECRET` must be a **Base64-encoded** string that decodes to at least 256 bits. Generate one with:
> `openssl rand -base64 32`

### 4. Run the frontend

```bash
cd url-shortener-react
npm install
npm run dev
```

App available at **http://localhost:5173**

Frontend env (`url-shortener-react/.env`):

```env
VITE_BACKEND_URL=http://localhost:8080
VITE_REACT_FRONT_END_URL=http://localhost:5173
VITE_REACT_SUBDOMAIN=http://localhost:5173/s
```

---

## Usage

1. Open the app and **register** a new account
2. **Log in** to access the dashboard
3. Create a short URL from a long destination link
4. Share `http://localhost:5173/s/{code}` or hit the API redirect at `http://localhost:8080/{code}`
5. Return to the dashboard to view click analytics

---

## API Reference

| Method | Endpoint | Auth | Description |
| --- | --- | --- | --- |
| `POST` | `/api/auth/public/register` | No | Create a new user |
| `POST` | `/api/auth/public/login` | No | Authenticate and receive a JWT |
| `POST` | `/api/urls/shorten` | Yes | Shorten a URL |
| `GET` | `/api/urls/myurls` | Yes | List the authenticated user's URLs |
| `GET` | `/api/urls/analytics/{shortUrl}` | Yes | Per-link clicks by date range |
| `GET` | `/api/urls/totalClicks` | Yes | Aggregate clicks by date range |
| `GET` | `/{shortUrl}` | No | Redirect to the original URL and log the click |

Authenticated requests require:

```http
Authorization: Bearer <jwt>
```

---

## Screenshots

> Add screenshots of the landing page and dashboard here after cloning to GitHub:
>
> `docs/landing.png` · `docs/dashboard.png`

---

## Stopping Local Services

```bash
# Stop backend and frontend with Ctrl+C in their terminals
docker compose down
```

---

## Future Improvements

- Custom aliases for short links
- Link expiration and soft-delete
- Rate limiting on public redirect endpoints
- Production deployment (Dockerized API + managed PostgreSQL)
- Unit and integration test coverage

---

## Acknowledgments

Built while following the EmbarkX full-stack Bitly clone course by Faisal Memon.  
Reference implementation: [EmbarkXOfficial/url-shortener-project](https://github.com/EmbarkXOfficial/url-shortener-project)

---

## License

This project is intended for personal learning and portfolio use. If you publish it, add a license of your choice (e.g. MIT).
