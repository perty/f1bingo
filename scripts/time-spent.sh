#!/usr/bin/env bash
echo -e "Date\t\tFirst\tLast\tTime\tLines"
git log --no-merges --format='%ad %H' --shortstat --date=format:'%Y-%m-%d %H:%M' | \
awk '
function timeToMinutes(time) {
    split(time, parts, ":")
    return parts[1] * 60 + parts[2]
}
function minutesToHHMM(minutes) {
    hours = int(minutes / 60)
    minutes = minutes % 60
    return sprintf("%02d:%02d", hours, minutes)
}
/^[0-9]{4}-[0-9]{2}-[0-9]{2}/ {
    currentDate = substr($0, 1, 10)
    currentTime = substr($0, 12, 5)
    if (!(currentDate in lastCommit)) {
        lastCommit[currentDate] = currentTime
    }
    firstCommit[currentDate] = currentTime
}
/^[ 0-9]/ {
    split($0, changes, ", ")
    for (i in changes) {
        if (changes[i] ~ /insertion/) {
            split(changes[i], added, " ")
            additions[currentDate] += added[1]
        } else if (changes[i] ~ /deletion/) {
            split(changes[i], deleted, " ")
            deletions[currentDate] += deleted[1]
        }
    }
}
END {
    totalDiffTime = 0
    totalChanges = 0
    for (date in firstCommit) {
        startTime = timeToMinutes(firstCommit[date])
        endTime = timeToMinutes(lastCommit[date])
        diffTime = endTime - startTime
        printf "%s\t%s\t%s\t%s\t%d\n",
          date, firstCommit[date], lastCommit[date], minutesToHHMM(diffTime), (additions[date] + deletions[date])
        totalDiffTime += diffTime
        totalChanges += (additions[date] + deletions[date])
    }
    printf "Total time: %s, Total changed lines: %d\n", minutesToHHMM(totalDiffTime), totalChanges
}' | sort
