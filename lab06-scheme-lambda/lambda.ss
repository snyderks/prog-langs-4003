; Prime number generation lab with lambda
; Completed by Kristian Snyder

(define (nlist n) ; Infinite list generator starting at 1
  (cons 
    n 
    (lambda () 
      (nlist (+ n 1)))
  )
)

(define (take n lst) ; retrieves n numbers from an infinite list lst
  (if (or (= n 0) (null? lst))
        '()
        (cons (car lst) (take (- n 1) ((cdr lst))))
  )
)

(define (splice-mults-of n lst) ; remove multiples of n from an infinite list
    (if (null? lst)
        '()
        (if (= (modulo (car lst) n) 0)
            (splice-mults-of n ((cdr lst)))
            (cons (car lst) (lambda () (splice-mults-of n ((cdr lst)))))
        )
    )
)

(define (primes lst) ; Return the first n primes in an infinite list of integers
    (if (null? lst)
      '()
      (cons (car lst) (lambda () (primes (splice-mults-of (car lst) ((cdr lst))))))
    )
)

; the final procedure to generate primes.
(define (stol$ n) (take n (primes (nlist 2))))
