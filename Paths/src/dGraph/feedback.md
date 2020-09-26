### HW5 Feedback

**CSE 331 18sp**

**Name:** Bill Tak Phung (phungb)

**Graded By:** Leah Perlmutter (cse331-staff@cs.washington.edu)

### Score: 78/92
---

**Problem 1 - Written Exercises:** 17/23

- a. IntQueue1: abstraction fcn missing a description of the ordering of elements (-1)
- a. IntQueue2: rep invariant missing upper bound on size (-1)
- a. IntQueue2: abstraction fcn missing description of wraparound behavior (-1)
- b. Good Job!
- c. Part 3: incomplete answer (-2)
- c. Parts 2, 5: rep exposure could be prevented by making a copy (-1)

**Problem 2 - Graph Specification:** 23/25

- API/Javadoc: 14/15
  - The `@modifies` clause on a method should have a list of objects visible to the client that are modified by the method. Elements of the list can include `this`, public fields in the spec of `this`, and any non-primitive parameters passed in to the method. (-1)

- Writeup: 9/10
  - public methods implemented but missing from writeup: getNodes (-1)

**Problem 3 - Tests :** 12/12

- Good Job!

**Problem 4 - Graph Implementation:** 20/25

- Correctness: 8/10
  - Failed some staff tests that expected multiple edges to exist between the same pair of nodes and some because child nodes were not listed in alphabetical order ("n10" < "n2"). (-2)
- Style: 7/10
  - Private methods should be documented.
  - Abstraction function should describe how the keys and values in instance field `dGraph` represent nodes and edges of the graph. (-1)
  - Rep invariant should be described in terms of instance fields. (-1)
  - Instance fields should be declared as interface types (e.g. Map instead of HashMap) (-1)
  

- Writeup: 5/5
  - Good Job!

**Problem 5 - Test Driver:** 5/5
- Good Job!

**Turnin:** 2/2

