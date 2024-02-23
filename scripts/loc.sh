#!/usr/bin/env bash

grand_total=0
count_lines() {
    file_extension=$1

    total_lines=$(find . -name "*.${file_extension}" -exec wc -l {} + | grep -vE '^[[:space:]0-9[:space:]]*total$' | awk '{sum += $1} END {print sum}')

    echo "Lines: ${file_extension} files: ${total_lines}"
    grand_total=$((grand_total + total_lines))
}

count_lines "java"
count_lines "html"
count_lines "css"
echo "Total lines: $grand_total"