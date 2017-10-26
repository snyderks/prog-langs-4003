; Lab 7 - Call with current continuation
; Completed by Kristian Snyder

; Alias long call-cc name
(define call/cc call-with-current-continuation)

; Part 1

(define multiply ; Try (multiply '(1 2 3 4 5))
    (lambda (k) 
        (let ((halt (call/cc (lambda (j) j)))) ; Set up the call-cc
            (if (procedure? halt) ; Jump out if we're done
                (letrec 
                    ((multiplier (lambda (l) ; The actual multiplying here
                        (if (null? l)
                            1
                            (if (zero? (car l))
                                (halt 0) 
                                (* (car l) (multiplier (cdr l))))))))
                    (halt (multiplier k))))
            halt)))

; Part 2

(define (pl lst) ; A generator that displays the list
    (define (controller lda)
        (for-each ; Go through the list
            (lambda (el)
                (set! lda 
                    (call/cc
                        (lambda (jump-in-here)
                            (set! controller jump-in-here)
                            (lda el)))))
            lst)
        (lda 'done))
    (define (gen) (call/cc controller))
    gen)

(define counter 0)

(define (main first second third)
    (let ((queue (list (pl first) (pl second) (pl third))))
        ((lambda (f) (f f))
            (lambda (x)
                (let ((halt (call/cc (lambda (j) j)))) ; Set up the call-cc
                    (if (procedure? halt)
                            (begin
                                (for-each
                                    (lambda (el)
                                        (let ((result (el)))
                                            (begin 
                                                (if (equal? result 'done)
                                                    (if (= counter 3) 
                                                        (halt 'done)
                                                        (set! counter (+ counter 1)))
                                                    (display result)))))
                                    queue)
                            (x x))))))))