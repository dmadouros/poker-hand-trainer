# Limit Hold 'em Starting Hand Trainer

This is a limit hold'em poker hand trainer based on recommendations from [_Getting Started in Hold 'em_ by Ed Miller](https://a.co/d/0ipEkE8q)

You will be dealt a series of hands. Given your hand, your starting 
position, and any action that has taken place ahead of you, you will be asked what your 
action should be (raise, check, bet, call, fold). The application will 
then take the same information and compare your answer to the correct 
answer. You get 1 point if your answer is correct and 0 points if your 
answer is incorrect.

And the end of the series of hands, you will be shown your total score 
and some basic statistics:
- what positions you were given
- what previous actions you were given
- what actions you took

Mostly these stats are for debugging purposes. Specifically, the 
'previous actions' as these are weighted for a game with little pre-flop 
raising.

Good luck; have fun!

### To run the application:

`./run.sh`

## Dev Notes

### Problems I Ran Into

1. If `run.sh` is not executable, try: `chmod u+x run.sh`
2. I use `iTerm2` and the font I was using (Inconsolata Nerd Font) used a 
different (and much worse) &hearts; icon than I wanted. To fix this, I 
switched to using the Menlo font during development.

### Miscellaneous

- In theory, this application works with any training set of opening hand 
data. You would need to replace the contents of the `rules` attribute in 
`me.dmadouros.pokerhand.infrastructure.RuleSet`. It was a PITA to 
manually transform this data from the structure provided in the book to 
the data structure used here and if someone (maybe me) wanted to spend 
some time writing a better translation mechanism from the book's notation 
system to how it is stored in the table or just a better data structure, 
this could be a fun project for another time.
- I opted to use [Mordant](https://github.com/ajalt/mordant) as a simpler 
approach to a text-based application. Mostly, I wanted to have something 
that would easily clear the screen in a built-in way. I'm not 100% 
certain Mordant satisfied my desire. It has some cool features. Most of
which I never used. Despite what Google's AI told me, Mordant does not 
have a built-in `clearScreen` method. It does have a way to do it, but 
it's rather verbose and I ended up implementing an extension function to 
get what I wanted. The only other feature I took advantage of was the 
library's `prompt` feature. This allowed me to ask the user for some 
input and has built-in validation and hint text, so I went with it. But, 
I think I could have pretty easily written this on my own. Maybe someday, 
I'll use more of Mordant's capabilities. Or, maybe I'll just rip it out.
- I'm really proud of the way that the domain classes came out. However, 
I didn't put any effort into separating the UI from the application or 
the domain. If someone was looking for ways to improve this, this is an 
area that could use it. At the moment, it doesn't seem worth it. But, if 
someone wanted to create a different UI (e.g. a javascript front end that 
ran in a browser), the 'need' for this separation increases. Maybe 
someday. 

---
## More on Translating from Book Notation to Internal Data Structure

The book's format is exampled below. Based on the poker books I've read, 
this seems like a fairly common layout.

```
                           Unraised Pots
|---------------------|----------------------|------------------|
| *Early Position*    | *Middle Position*    | *Late Position*  |
|                     |                      |                  |
| *Raise:* AA-TT,     | *Raise:* AA-99, AKs- | *Raise:* AA-88,  |                
| AKs, ATs, KQs,      | ATs, KQs-KJs, AK-    | AKs-A8s, KQs-    |
| and AK-AQ           | AJ, and KQ           | KTs, QJs, AK-    |
|                     |                      | AT, and KQ-KJ    |
|                     |                      |                  |
| *Call*: 99-77, KJs, | *Call:* 88-22, A9s-  | *Call:* 77-22,   |
| QJs, AJ, and KQ     | A7s, KTs, QJs-QTs,   | A7s-A2s, K9s,    |
|                     | JTs, AT, and KJ      | QTs-Q9s, JTs-    |
|                     |                      | 87s, and J9s-T8s |
|---------------------|----------------------|------------------|
| *Small Blind*       | *Big Blind*          |                  |
|                     |                      |                  |
| *Raise:* ...        | *Raise:* ...         |                  |
|                     |                      |                  |
| *Call:* ...         | *Check:* Everything  |                  |
|                     | else                 |                  |
|---------------------|----------------------|------------------|
```

There's a similar table for Raised Pots.

Let's translate some of the Late Position block for our purposes. I believe 
that the examples I've selected represent all varieties of transformations.

AA-88 means any pocket pair between Ace-Ace (AA) and Eight-Eight (88). 
Note that both cards change in this transformation. 
This translates to:

| Hand | Previous Action | Position | Action |
|------|-----------------|----------|--------|
| AA   | Unraised        | Late     | Raise  |
| KK   | Unraised        | Late     | Raise  |
| QQ   | Unraised        | Late     | Raise  |
| JJ   | Unraised        | Late     | Raise  |
| TT   | Unraised        | Late     | Raise  |
| 99   | Unraised        | Late     | Raise  |
| 88   | Unraised        | Late     | Raise  |

AKs-A8s means any suited Ace where the non-Ace is at least an Eight (8).
Note that only one card changes in this transformation. 
This translates to:

| Hand | Previous Action | Position | Action |
|------|-----------------|----------|--------|
| AKs  | Unraised        | Late     | Raise  |
| AQs  | Unraised        | Late     | Raise  |
| AJs  | Unraised        | Late     | Raise  |
| ATs  | Unraised        | Late     | Raise  |
| A9s  | Unraised        | Late     | Raise  |
| A8s  | Unraised        | Late     | Raise  |

AK-AT means any unsuited Ace where the non-Ace is at least a Ten (T). 
Note that only one card changes in this transformation. 
This translates to:

| Hand | Previous Action | Position | Action |
|------|-----------------|----------|--------|
| AK   | Unraised        | Late     | Raise  |
| AQ   | Unraised        | Late     | Raise  |
| AJ   | Unraised        | Late     | Raise  |
| AT   | Unraised        | Late     | Raise  |

JTs-87s means any suited connectors with no gap between Jack-Ten (JT) and Eight-Seven (87). 
Note that both cards change in this transformation. 
This translates to:

| Hand | Previous Action | Position | Action |
|------|-----------------|----------|--------|
| JTs  | Unraised        | Late     | Call   |
| T9s  | Unraised        | Late     | Call   |
| 98s  | Unraised        | Late     | Call   |
| 87s  | Unraised        | Late     | Call   |

J9s-T8s means any suited connectors with 1 gap between Jack-Nine (J9) and Ten-Eight (T8). 
Note that both cards change in this transformation. 
This translates to:

| Hand | Previous Action | Position | Action |
|------|-----------------|----------|--------|
| J9s  | Unraised        | Late     | Call   |
| T8s  | Unraised        | Late     | Call   |

Therefore, the entire Late Position section translates to:

| Hand | Previous Action | Position | Action |
|------|-----------------|----------|--------|
| AA   | Unraised        | Late     | Raise  |
| KK   | Unraised        | Late     | Raise  |
| QQ   | Unraised        | Late     | Raise  |
| JJ   | Unraised        | Late     | Raise  |
| TT   | Unraised        | Late     | Raise  |
| 99   | Unraised        | Late     | Raise  |
| 88   | Unraised        | Late     | Raise  |
| AKs  | Unraised        | Late     | Raise  |
| AQs  | Unraised        | Late     | Raise  |
| AJs  | Unraised        | Late     | Raise  |
| ATs  | Unraised        | Late     | Raise  |
| A9s  | Unraised        | Late     | Raise  |
| A8s  | Unraised        | Late     | Raise  |
| KQs  | Unraised        | Late     | Raise  |
| KJs  | Unraised        | Late     | Raise  |
| KTs  | Unraised        | Late     | Raise  |
| QJs  | Unraised        | Late     | Raise  |
| AK   | Unraised        | Late     | Raise  |
| AQ   | Unraised        | Late     | Raise  |
| AJ   | Unraised        | Late     | Raise  |
| AT   | Unraised        | Late     | Raise  |
| KQ   | Unraised        | Late     | Raise  |
| KJ   | Unraised        | Late     | Raise  |
| 77   | Unraised        | Late     | Call   |
| 66   | Unraised        | Late     | Call   |
| 55   | Unraised        | Late     | Call   |
| 44   | Unraised        | Late     | Call   |
| 33   | Unraised        | Late     | Call   |
| 22   | Unraised        | Late     | Call   |
| A7s  | Unraised        | Late     | Call   |
| A6s  | Unraised        | Late     | Call   |
| A5s  | Unraised        | Late     | Call   |
| A4s  | Unraised        | Late     | Call   |
| A3s  | Unraised        | Late     | Call   |
| A2s  | Unraised        | Late     | Call   |
| K9s  | Unraised        | Late     | Call   |
| QTs  | Unraised        | Late     | Call   |
| Q9s  | Unraised        | Late     | Call   |
| JTs  | Unraised        | Late     | Call   |
| T9s  | Unraised        | Late     | Call   |
| 98s  | Unraised        | Late     | Call   |
| 87s  | Unraised        | Late     | Call   |
| J9s  | Unraised        | Late     | Call   |
| T8s  | Unraised        | Late     | Call   |