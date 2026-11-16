#!/usr/bin/env bash
set -e

BASE_URL="${1:-http://localhost:8080}"

echo "1. Checking Actuator Health..."
curl -fsS "$BASE_URL/actuator/health" | grep '"status":"UP"'

echo "2. Creating Seat A-101..."
SEAT_JSON=$(curl -fsS -X POST "$BASE_URL/seats" \
  -H "Content-Type: application/json" \
  -d '{"seatNumber":"A-101","zone":"Silent Study","floor":1,"capacity":1,"hasPowerOutlet":true}')
SEAT_ID=$(echo "$SEAT_JSON" | python3 -c "import sys, json; print(json.load(sys.stdin)['id'])")

echo "3. Listing Available Seats..."
curl -fsS "$BASE_URL/seats/available" | grep "A-101"

echo "4. Reserving Seat A-101..."
RES_JSON=$(curl -fsS -X POST "$BASE_URL/reservations" \
  -H "Content-Type: application/json" \
  -d "{\"seatId\":$SEAT_ID,\"studentName\":\"Meghanshu Kumar\",\"studentEmail\":\"meghanshu@msrit.edu\",\"studentUsn\":\"1MS24CY035\",\"durationHours\":2}")
RES_ID=$(echo "$RES_JSON" | python3 -c "import sys, json; print(json.load(sys.stdin)['id'])")

echo "5. Checking Active Reservations..."
curl -fsS "$BASE_URL/reservations/active" | grep "1MS24CY035"

echo "6. Releasing Seat Reservation..."
curl -fsS -X PUT "$BASE_URL/reservations/$RES_ID/release" | grep "RELEASED"

echo "7. Verifying Seat A-101 is AVAILABLE again..."
curl -fsS "$BASE_URL/seats/$SEAT_ID" | grep "AVAILABLE"

echo "All 7 E2E transactions verified successfully!"
