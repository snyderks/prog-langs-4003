; Prime number generation lab
; Completed by Kristian Snyder

(define (nlist n m acc) ; Create a list from n to m, inclusive.
    (if (< m n)
        acc
        (nlist n (- m 1) (cons m acc))
    )
)

(define (rangelist n m) ; Nice wrapper for nlist that provides the accumulator
    (nlist n m '())
)

(define (splice-mults-of n lst) ; remove multiples of n from the list
    (if (pair? lst)
        (if (= (modulo (car lst) n) 0)
            (splice-mults-of n (cdr lst))
            (cons (car lst) (splice-mults-of n (cdr lst)))
        )
        '()
    )
)

(define (primes n lst acc) ; Return the first n primes in a list of integers
    (if (pair? lst)
        (if (zero? n)
            acc
            (primes (- n 1) (splice-mults-of (car lst) (cdr lst)) (append acc (list (car lst))))
        )
        (write "Failed: not enough integers")
    )
)

; the final procedure to generate primes.
(define (stol n) (primes n (rangelist 2 (* n 10)) '()))
